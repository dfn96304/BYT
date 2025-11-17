package BYT.Helpers;

// AI-generated, see PDF
import java.io.*;
import java.lang.reflect.*;
import java.nio.file.*;
import java.util.*;

/**
 * Helper / utility class for saving and loading all "extents".
 *
 * In this project an *extent* is a static List stored in each domain
 * class that keeps track of all created instances of that class.
 *
 * This helper knows how to:
 *  - walk over a set of owner classes
 *  - read their static `extent` fields using reflection
 *  - serialize them all into one file
 *  - later read that file back and restore the lists
 */
public final class ExtentIO {

    /**
     * Private constructor + 'final' class:
     *  - 'final' on the class means nobody can subclass it (no `extends ExtentIO`).
     *  - private constructor means nobody can create an instance (`new ExtentIO()`).
     *
     * Together this is the standard pattern for a "static utility" class.
     */
    private ExtentIO() {}

    /**
     * Save *all* static extents of the given owner classes into a single file.
     *
     * @param file   path to file on disk (e.g. "extents.bin")
     * @param owners classes that own an `extent` list
     *
     * The parameter type `Class<?>... owners`:
     *  - `Class<T>` is Java's runtime representation of a class (e.g. Chef.class).
     *  - The `?` is a *wildcard type parameter*: it literally means
     *    "some unknown type, we don't care which one".
     *    So `Class<?>` = "a Class of *something*".
     *  - `...` makes it a *varargs* parameter: callers can pass any number
     *    of Class objects and they arrive as an array.
     */
    public static void saveAll(String file, Class<?>... owners) throws IOException {

        /*
         * Map<String, List<?>> snapshot = new LinkedHashMap<>();
         *
         *  - Map<K,V> maps keys of type K to values of type V.
         *  - Here:
         *        K = String    (fully qualified class name)
         *        V = List<?>   (a List of *some* element type, unknown here)
         *
         *  - The `<?>` on List<?> is a wildcard: "a list of something,
         *    but we don't know or care which class exactly".
         *
         *  - LinkedHashMap instead of HashMap:
         *      * HashMap does *not* guarantee any iteration order.
         *      * LinkedHashMap remembers the order in which keys were inserted.
         *        So when we iterate later, classes come out in the same order
         *        we put them in, which makes file layout more stable/predictable.
         */
        Map<String, List<?>> snapshot = new LinkedHashMap<>();

        // For every owner class in the varargs array...
        for (Class<?> owner : owners) {
            // Read that class's static `extent` field (see helper below).
            List<?> extent = getExtentList(owner);

            // Store it in the map under the class's fully qualified name.
            snapshot.put(owner.getName(), extent);
        }

        /*
         * Actually write the map into a binary file.
         *
         * - Path.of(file) builds a java.nio.file.Path from the String.
         * - Files.newOutputStream(path) opens a byte stream for writing.
         * - ObjectOutputStream wraps that stream and allows us to write
         *   entire Java objects (anything Serializable).
         *
         * A try-with-resources block automatically closes both streams,
         * even if an exception is thrown.
         */
        Path path = Path.of(file);
        try (OutputStream out = Files.newOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(out)) {

            // Serialize the entire Map<String, List<?>> object graph.
            oos.writeObject(snapshot);
        }
    }

    /**
     * Load extents from the given file and push them back into the static
     * `extent` fields of the given owner classes.
     *
     * @param file   path to file on disk (e.g. "extents.bin")
     * @param owners classes whose extents should be restored
     *
     * @throws IOException            when there is a low-level I/O problem
     * @throws ClassNotFoundException when the file refers to a class that
     *                                is not available on the current classpath
     */
    public static void loadAll(String file, Class<?>... owners)
            throws IOException, ClassNotFoundException {

        Path path = Path.of(file);

        // Read the serialized object graph back from disk.
        Object obj;
        try (InputStream in = Files.newInputStream(path);
             ObjectInputStream ois = new ObjectInputStream(in)) {

            obj = ois.readObject();
        }

        /*
         * We *expect* the file to contain a Map<String, List<?>>,
         * because that's exactly what saveAll wrote.
         *
         * We check the shape at runtime and fail fast if
         * the format looks wrong.
         */
        if (!(obj instanceof Map)) {
            throw new IllegalArgumentException("Extent file has unexpected format: not a Map");
        }

        /*
         * This cast is "unchecked" from the compiler's point of view because
         * generics are erased at runtime – the JVM only sees a raw Map.
         *
         * We know what we wrote, though, so this is safe *in this context*.
         * The @SuppressWarnings annotation tells the compiler:
         * "Yes, I know this is unchecked; I'm doing it deliberately."
         */
        @SuppressWarnings("unchecked")
        Map<String, List<?>> snapshot = (Map<String, List<?>>) obj;

        // For each owner class, restore its static extent field.
        for (Class<?> owner : owners) {
            // Find the list for this class (may be null if nothing was saved).
            List<?> rawList = snapshot.get(owner.getName());

            // If nothing was stored, use an empty list; otherwise, cast it to List<Object>.
            List<Object> loadedList = (rawList == null)
                    ? new ArrayList<>()
                    : castToObjectList(rawList);

            try {
                /*
                 * Locate the static field named "extent" on the class.
                 *
                 * Reflection basics:
                 *  - Class<?> represents a class at runtime.
                 *  - getDeclaredField("extent") returns a Field object even if
                 *    the field is private.
                 */
                Field f = owner.getDeclaredField("extent");

                // Allow access even if the field is private / package-private, etc.
                f.setAccessible(true);

                /*
                 * 1. Get the existing List instance from the field.
                 * 2. Clear its contents.
                 * 3. Add all elements from the loaded list.
                 */
                Object currentValue = f.get(null);

                if (!(currentValue instanceof List<?>)) {
                    throw new IllegalArgumentException(owner.getName() + ".extent must be a List");
                }

                @SuppressWarnings("unchecked")
                List<Object> currentList = (List<Object>) currentValue;

                // Reset current contents
                currentList.clear();

                // If there was data saved for this class, add it
                if (loadedList != null) {
                    currentList.addAll(loadedList);
                }
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                // Wrap reflection errors into an unchecked exception with context.
                throw new IllegalArgumentException(
                        "Cannot access 'extent' in " + owner.getName(), ex);
            }
        }
    }

    // -------------------------------------------------------------------------
    // Reflection helper: read the static `extent` field value from a class.
    // -------------------------------------------------------------------------

    /**
     * Look up a static field called {@code extent} on the given class
     * and return its value as a List<?>, or throw if the setup is invalid.
     */
    private static List<?> getExtentList(Class<?> owner) {
        try {
            // Look up the field named "extent" (regardless of its access modifier).
            Field f = owner.getDeclaredField("extent");

            // Bypass Java's normal access checks (private/protected/package-private).
            f.setAccessible(true);

            /*
             * Because we're dealing with a *static* field, we pass null as the
             * instance parameter to get(...).
             */
            Object val = f.get(null);

            /*
             * We expect the field to be some kind of List<...>.
             *
             * `instanceof List<?>` uses a wildcard again:
             * "a List of *some* element type".
             * We don't care what that element type is here,
             * only that it's a List at all.
             */
            if (!(val instanceof List<?>)) {
                throw new IllegalArgumentException(owner.getName() + ".extent must be a List");
            }

            /*
             * The return type is List<?> because we *still* don't know what the
             * element type is (Chef, Customer, ...). The wildcard in List<?>
             * expresses exactly that: "a list of some unknown element type".
             */
            return (List<?>) val;
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            // If the field doesn't exist or isn't accessible, the class is misconfigured.
            throw new IllegalArgumentException(
                    "Cannot access 'extent' in " + owner.getName(), ex);
        }
    }

    /**
     * Helper to "forget" the element type of a list and treat it as List&lt;Object&gt;.
     *
     * The compiler can't fully check this cast because of type erasure,
     * so it produces an "unchecked" warning, which we explicitly suppress.
     */
    @SuppressWarnings("unchecked")
    private static List<Object> castToObjectList(List<?> list) {
        /*
         * At runtime, List<Chef>, List<Customer> and List<Object> all look the same:
         * just some List whose elements are references.
         *
         * Because of that, this cast would normally trigger an "unchecked cast"
         * warning – the compiler can't *prove* that the elements are really
         * instances of Object (even though they are).
         *
         * The @SuppressWarnings("unchecked") annotation above says:
         *  "Dear compiler, I know this cast is technically unchecked,
         *   but in this context it's safe. Please don't show a warning here."
         */
        return (List<Object>) list;
    }
}
