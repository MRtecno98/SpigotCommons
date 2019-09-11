package com.spigot.libraries.unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;

import com.spigot.libraries.environment.Environment;
import com.spigot.libraries.utility.ReflectionUtils;

import sun.misc.Unsafe;

public class Unsafer {
	public static final String UNSAFE_FIELD_PRE_JDK9 = "theUnsafe";
	public static final String UNSAFE_FIELD_POST_JDK9 = "theInternalUnsafe";
	
	//Gets the Unsafe instance
	public static Unsafe getUnsafe() {
		try {
			return ReflectionUtils.getStaticPrivateField(Unsafe.class, 
					Environment.getVersion().getVersion() > 8 ? UNSAFE_FIELD_POST_JDK9 : UNSAFE_FIELD_PRE_JDK9);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//Makes a shallow copy of the object
    static Object shallowCopy(Object obj) {
        long size = sizeOf(obj);
        long start = toAddress(obj);
        long address = getUnsafe().allocateMemory(size);
        getUnsafe().copyMemory(start, address, size);
        return fromAddress(address);
    }
    
    //Object to memory address
    static long toAddress(Object obj) {
        Object[] array = new Object[] {obj};
        long baseOffset = getUnsafe().arrayBaseOffset(Object[].class);
        return normalize(getUnsafe().getInt(array, baseOffset));
    }
    
    //Object from memory address
    static Object fromAddress(long address) {
        Object[] array = new Object[] {null};
        long baseOffset = getUnsafe().arrayBaseOffset(Object[].class);
        getUnsafe().putLong(array, baseOffset, address);
        return array[0];
    }
    
    //Calculates size of an object
    public static long sizeOf(Object o) {
        Unsafe u = getUnsafe();
        HashSet<Field> fields = new HashSet<Field>();
        Class<?> c = o.getClass();
        while (c != Object.class) {
            for (Field f : c.getDeclaredFields()) {
                if ((f.getModifiers() & Modifier.STATIC) == 0) {
                    fields.add(f);
                }
            }
            c = c.getSuperclass();
        }

        // get offset
        long maxSize = 0;
        for (Field f : fields) {
            long offset = u.objectFieldOffset(f);
            if (offset > maxSize) {
                maxSize = offset;
            }
        }

        return ((maxSize/8) + 1) * 8;   // padding
    }
    
    //Unsigned int -> long
    private static long normalize(int value) {
        if(value >= 0) return value;
        return (~0L >>> 32) & value;
    }
}
