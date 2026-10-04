package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@OriginalClass("client!ed")
public final class ReflectionCheck extends Node {

	@OriginalMember(owner = "client!qi", name = "u", descriptor = "Lclient!ih;")
	public static LinkedList queue = new LinkedList();

	@OriginalMember(owner = "client!ed", name = "p", descriptor = "I")
	public int size;

	@OriginalMember(owner = "client!ed", name = "u", descriptor = "[Lsignlink!im;")
	public PrivilegedRequest[] methodRequests;

	@OriginalMember(owner = "client!ed", name = "v", descriptor = "[I")
	public int[] fieldValues;

	@OriginalMember(owner = "client!ed", name = "w", descriptor = "[I")
	public int[] errors;

	@OriginalMember(owner = "client!ed", name = "y", descriptor = "[I")
	public int[] types;

	@OriginalMember(owner = "client!ed", name = "B", descriptor = "[[[B")
	public byte[][][] methodArguments;

	@OriginalMember(owner = "client!ed", name = "C", descriptor = "[Lsignlink!im;")
	public PrivilegedRequest[] fieldRequests;

	@OriginalMember(owner = "client!ed", name = "F", descriptor = "I")
	public int id;

	@OriginalMember(owner = "client!t", name = "a", descriptor = "(Lclient!i;II)V")
	public static void loop(@OriginalArg(0) Packet out) {
		while (true) {
			@Pc(18) ReflectionCheck check = (ReflectionCheck) queue.head();
			if (check == null) {
				return;
			}
			@Pc(23) boolean pending = false;
			@Pc(25) int i;
			for (i = 0; i < check.size; i++) {
				if (check.fieldRequests[i] != null) {
					if (check.fieldRequests[i].status == 2) {
						check.errors[i] = -5;
					}
					if (check.fieldRequests[i].status == 0) {
						pending = true;
					}
				}
				if (check.methodRequests[i] != null) {
					if (check.methodRequests[i].status == 2) {
						check.errors[i] = -6;
					}
					if (check.methodRequests[i].status == 0) {
						pending = true;
					}
				}
			}
			if (pending) {
				return;
			}
			out.p1isaac(163);
			out.p1(0);
			i = out.offset;
			out.p4(check.id);
			for (@Pc(121) int j = 0; j < check.size; j++) {
				if (check.errors[j] == 0) {
					try {
						@Pc(151) int type = check.types[j];
						@Pc(168) Field field;
						@Pc(195) int value;
						if (type == 0) {
							field = (Field) check.fieldRequests[j].result;
							value = field.getInt(null);
							out.p1(0);
							out.p4(value);
						} else if (type == 1) {
							field = (Field) check.fieldRequests[j].result;
							field.setInt(null, check.fieldValues[j]);
							out.p1(0);
						} else if (type == 2) {
							field = (Field) check.fieldRequests[j].result;
							value = field.getModifiers();
							out.p1(0);
							out.p4(value);
						}
						@Pc(234) Method method;
						if (type == 3) {
							method = (Method) check.methodRequests[j].result;
							@Pc(239) byte[][] args = check.methodArguments[j];
							@Pc(243) Object[] params = new Object[args.length];
							for (@Pc(245) int k = 0; k < args.length; k++) {
								@Pc(259) ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(args[k]));
								params[k] = stream.readObject();
							}
							@Pc(272) Object result = method.invoke(null, params);
							if (result == null) {
								out.p1(0);
							} else if (result instanceof Number) {
								out.p1(1);
								out.p8(((Number) result).longValue());
							} else if (result instanceof JagString) {
								out.p1(2);
								out.pjstr((JagString) result);
							} else {
								out.p1(4);
							}
						} else if (type == 4) {
							method = (Method) check.methodRequests[j].result;
							value = method.getModifiers();
							out.p1(0);
							out.p4(value);
						}
					} catch (@Pc(338) ClassNotFoundException ex) {
						out.p1(-10);
					} catch (@Pc(344) InvalidClassException ex) {
						out.p1(-11);
					} catch (@Pc(350) StreamCorruptedException ex) {
						out.p1(-12);
					} catch (@Pc(356) OptionalDataException ex) {
						out.p1(-13);
					} catch (@Pc(362) IllegalAccessException ex) {
						out.p1(-14);
					} catch (@Pc(368) IllegalArgumentException ex) {
						out.p1(-15);
					} catch (@Pc(374) InvocationTargetException ex) {
						out.p1(-16);
					} catch (@Pc(380) SecurityException ex) {
						out.p1(-17);
					} catch (@Pc(386) IOException ex) {
						out.p1(-18);
					} catch (@Pc(392) NullPointerException ex) {
						out.p1(-19);
					} catch (@Pc(398) Exception ex) {
						out.p1(-20);
					} catch (@Pc(404) Throwable ex) {
						out.p1(-21);
					}
				} else {
					out.p1(check.errors[j]);
				}
			}
			out.addcrc(i);
			out.psize1(out.offset - i);
			check.unlink();
		}
	}

	@OriginalMember(owner = "client!qg", name = "a", descriptor = "(Lsignlink!ll;Lclient!wa;IB)V")
	public static void push(@OriginalArg(0) SignLink signLink, @OriginalArg(1) Buffer buf, @OriginalArg(2) int id) {
		@Pc(17) ReflectionCheck check = new ReflectionCheck();
		check.size = buf.g1();
		check.id = buf.g4();
		check.methodRequests = new PrivilegedRequest[check.size];
		check.errors = new int[check.size];
		check.methodArguments = new byte[check.size][][];
		check.fieldRequests = new PrivilegedRequest[check.size];
		check.types = new int[check.size];
		check.fieldValues = new int[check.size];
		for (@Pc(59) int i = 0; i < check.size; i++) {
			try {
				@Pc(71) int type = buf.g1();
				@Pc(93) String className;
				@Pc(104) String memberName;
				@Pc(95) int fieldValue;
				if (type == 0 || type == 1 || type == 2) {
					className = new String(buf.gjstr().toByteArray());
					fieldValue = 0;
					memberName = new String(buf.gjstr().toByteArray());
					if (type == 1) {
						fieldValue = buf.g4();
					}
					check.types[i] = type;
					check.fieldValues[i] = fieldValue;
					check.fieldRequests[i] = signLink.getDeclaredField(memberName, classForName(className));
				} else if (type == 3 || type == 4) {
					className = new String(buf.gjstr().toByteArray());
					memberName = new String(buf.gjstr().toByteArray());
					fieldValue = buf.g1();
					@Pc(171) String[] argClassNames = new String[fieldValue];
					for (@Pc(173) int j = 0; j < fieldValue; j++) {
						argClassNames[j] = new String(buf.gjstr().toByteArray());
					}
					@Pc(193) byte[][] argData = new byte[fieldValue][];
					@Pc(210) int k;
					if (type == 3) {
						for (@Pc(199) int m = 0; m < fieldValue; m++) {
							k = buf.g4();
							argData[m] = new byte[k];
							buf.gdata(k, argData[m]);
						}
					}
					check.types[i] = type;
					@Pc(234) Class<?>[] argClasses = new Class[fieldValue];
					for (k = 0; k < fieldValue; k++) {
						argClasses[k] = classForName(argClassNames[k]);
					}
					check.methodRequests[i] = signLink.getDeclaredMethod(classForName(className), argClasses, memberName);
					check.methodArguments[i] = argData;
				}
			} catch (@Pc(269) ClassNotFoundException ex) {
				check.errors[i] = -1;
			} catch (@Pc(276) SecurityException ex) {
				check.errors[i] = -2;
			} catch (@Pc(283) NullPointerException ex) {
				check.errors[i] = -3;
			} catch (@Pc(290) Exception ex) {
				check.errors[i] = -4;
			} catch (@Pc(297) Throwable ex) {
				check.errors[i] = -5;
			}
		}
		queue.addTail(check);
	}

	@OriginalMember(owner = "client!j", name = "c", descriptor = "(I)V")
	public static void clear() {
		queue = new LinkedList();
	}

	@OriginalMember(owner = "client!ag", name = "a", descriptor = "(ILjava/lang/String;)Ljava/lang/Class;")
	public static Class<?> classForName(@OriginalArg(1) String name) throws ClassNotFoundException {
		if (name.equals("B")) {
			return Byte.TYPE;
		} else if (name.equals("I")) {
			return Integer.TYPE;
		} else if (name.equals("S")) {
			return Short.TYPE;
		} else if (name.equals("J")) {
			return Long.TYPE;
		} else if (name.equals("Z")) {
			return Boolean.TYPE;
		} else if (name.equals("F")) {
			return Float.TYPE;
		} else if (name.equals("D")) {
			return Double.TYPE;
		} else if (name.equals("C")) {
			return Character.TYPE;
		} else {
			return Class.forName(name);
		}
	}
}
