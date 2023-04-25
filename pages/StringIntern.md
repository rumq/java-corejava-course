>[Pages Home](Home.md)

# String Interning

See also

- [Strings](Strings.md)


From the Java API documentation for [intern()](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/String.html#intern()) method

> returns a string that has the same contents as this string, but is guaranteed to be from a pool of unique strings.

> A pool of strings, initially empty, is maintained privately by the class String.

> When the intern method is invoked, if the pool already contains a string equal to this String object as determined by the equals(Object) method, then the string from the pool is returned. Otherwise, this String object is added to the pool and a reference to this String object is returned.

> All literal strings and string-valued constant expressions are interned


Here are some examples to illustrate the above points.

```java

// All literals are interned, i.e they are stored in the string pool
String name1 = "Deepika";
String name2 = "Deepika";
String name3 = "D" + "e" + "e" + "p" + "i" + "k" + "a";
String name4 = new String("Deepika").intern();
String name5 = new String("Deepika");

// Same hashcodes
System.out.println(name1.hashCode());
System.out.println(name2.hashCode());
System.out.println(name3.hashCode());
System.out.println(name4.hashCode());
System.out.println(name5.hashCode());

// All equal
System.out.println(name1.equals(name2)); // true
System.out.println(name1.equals(name3)); // true
System.out.println(name1.equals(name4)); // true
System.out.println(name1.equals(name5)); // true

// == is tricky
System.out.println(name1 == name2); // true
System.out.println(name2 == "Deepika"); // true

// Concatenating literals will also result in a literal
System.out.println(name2 == name3); // true
System.out.println((name1 + name2).equals(name1 + name2)); // true

// Interning will create a literal object
System.out.println(name1 == name4); // true

// new String() will create a new object
System.out.println(name3 == name5); // false
// If not interned, new String() will create a new object
System.out.println(name4 == name5); // false
// Concatenating objects will create a new object each time
System.out.println((name1 + name2) == (name1 + name2)); // false

```
>[Pages Home](Home.md)