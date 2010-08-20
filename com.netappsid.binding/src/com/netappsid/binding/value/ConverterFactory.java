package com.netappsid.binding.value;

import java.text.Format;
import java.text.ParseException;

import com.jgoodies.binding.value.ValueModel;
import com.netappsid.binding.beans.support.ChangeSupportFactory;

public final class ConverterFactory
{
	private final ChangeSupportFactory changeSupportFactory;
	
	public ConverterFactory(ChangeSupportFactory changeSupportFactory)
	{
		this.changeSupportFactory = changeSupportFactory;
	}

	/**
	 * Creates and returns a ValueModel that negates Booleans and leaves null unchanged.
	 * <p>
	 * 
	 * <strong>Constraints:</strong> The subject is of type <code>Boolean</code>.
	 * 
	 * @param booleanSubject
	 *            a Boolean ValueModel
	 * @return a ValueModel that inverts Booleans
	 * 
	 * @throws NullPointerException
	 *             if the subject is {@code null}
	 */
	public ValueModel createBooleanNegator(ValueModel booleanSubject)
	{
		return new BooleanNegator(changeSupportFactory, booleanSubject);
	}

	/**
	 * Creates and returns a ValueModel that converts Booleans to the associated of the two specified strings, and vice versa. Null values are mapped to an
	 * empty string. Ignores cases when setting a text.
	 * <p>
	 * 
	 * <strong>Constraints:</strong> The subject is of type <code>Boolean</code>.
	 * 
	 * @param booleanSubject
	 *            a Boolean ValueModel
	 * @param trueText
	 *            the text associated with <code>Boolean.TRUE</code>
	 * @param falseText
	 *            the text associated with <code>Boolean.FALSE</code>
	 * 
	 * @return a ValueModel that converts boolean to the associated text
	 * 
	 * @throws NullPointerException
	 *             if the subject, trueText or falseText is {@code null}
	 * @throws IllegalArgumentException
	 *             if the trueText equals the falseText
	 */
	public ValueModel createBooleanToStringConverter(ValueModel booleanSubject, String trueText, String falseText)
	{
		return createBooleanToStringConverter(booleanSubject, trueText, falseText, "");
	}

	/**
	 * Creates and returns a ValueModel that converts Booleans to the associated of the two specified strings, and vice versa. Null values are mapped to the
	 * specified text. Ignores cases when setting a text.
	 * <p>
	 * 
	 * <strong>Constraints:</strong> The subject is of type <code>Boolean</code>.
	 * 
	 * @param booleanSubject
	 *            a Boolean ValueModel
	 * @param trueText
	 *            the text associated with <code>Boolean.TRUE</code>
	 * @param falseText
	 *            the text associated with <code>Boolean.FALSE</code>
	 * @param nullText
	 *            the text associated with {@code null}
	 * 
	 * @return a ValueModel that converts boolean to the associated text
	 * 
	 * @throws NullPointerException
	 *             if the subject, trueText, falseText or nullText is {@code null}
	 * @throws IllegalArgumentException
	 *             if the trueText equals the falseText
	 */
	public ValueModel createBooleanToStringConverter(ValueModel booleanSubject, String trueText, String falseText, String nullText)
	{
		return new BooleanToStringConverter(changeSupportFactory, booleanSubject, trueText, falseText, nullText);
	}

	/**
	 * Creates and returns a ValueModel that converts Doubles using the specified multiplier.
	 * <p>
	 * 
	 * Examples: multiplier=100, Double(1.23) -> Double(123), multiplier=1000, Double(1.23) -> Double(1230)
	 * <p>
	 * 
	 * <strong>Constraints:</strong> The subject is of type <code>Double</code>.
	 * 
	 * @param doubleSubject
	 *            a Double ValueModel
	 * @param multiplier
	 *            the multiplier used for the conversion
	 * 
	 * @return a ValueModel that converts Doubles using the specified multiplier
	 * 
	 * @throws NullPointerException
	 *             if the subject is {@code null}
	 * 
	 * @since 1.0.2
	 */
	public ValueModel createDoubleConverter(ValueModel doubleSubject, double multiplier)
	{
		return new DoubleConverter(changeSupportFactory, doubleSubject, multiplier);
	}

	/**
	 * Creates and returns a ValueModel that converts Doubles to Integer, and vice versa.
	 * <p>
	 * 
	 * <strong>Constraints:</strong> The subject is of type <code>Double</code>.
	 * 
	 * @param doubleSubject
	 *            a Double ValueModel
	 * 
	 * @return a ValueModel that converts Doubles to Integer
	 * 
	 * @throws NullPointerException
	 *             if the subject is {@code null}
	 */
	public ValueModel createDoubleToIntegerConverter(ValueModel doubleSubject)
	{
		return createDoubleToIntegerConverter(doubleSubject, 1);
	}

	/**
	 * Creates and returns a ValueModel that converts Doubles to Integer, and vice versa. The multiplier can be used to convert Doubles to percent, permill,
	 * etc. For a percentage, set the multiplier to be 100, for a permill, set the multiplier to be 1000.
	 * <p>
	 * 
	 * Examples: multiplier=100, Double(1.23) -> Integer(123), multiplier=1000, Double(1.23) -> Integer(1230)
	 * <p>
	 * 
	 * <strong>Constraints:</strong> The subject is of type <code>Double</code>.
	 * 
	 * @param doubleSubject
	 *            a Double ValueModel
	 * @param multiplier
	 *            the multiplier used to convert the Double to Integer
	 * 
	 * @return a ValueModel that converts Doubles to Integer
	 * 
	 * @throws NullPointerException
	 *             if the subject is {@code null}
	 */
	public ValueModel createDoubleToIntegerConverter(ValueModel doubleSubject, int multiplier)
	{
		return new DoubleToIntegerConverter(changeSupportFactory, doubleSubject, multiplier);
	}

	/**
	 * Creates and returns a ValueModel that converts Floats using the specified multiplier.
	 * <p>
	 * 
	 * Examples: multiplier=100, Float(1.23) -> Float(123), multiplier=1000, Float(1.23) -> Float(1230)
	 * <p>
	 * 
	 * <strong>Constraints:</strong> The subject is of type <code>Float</code>.
	 * 
	 * @param floatSubject
	 *            a Float ValueModel
	 * @param multiplier
	 *            the multiplier used for the conversion
	 * 
	 * @return a ValueModel that converts Float using the specified multiplier
	 * 
	 * @throws NullPointerException
	 *             if the subject is {@code null}
	 * 
	 * @since 1.0.2
	 */
	public ValueModel createFloatConverter(ValueModel floatSubject, float multiplier)
	{
		return new FloatConverter(changeSupportFactory, floatSubject, multiplier);
	}

	/**
	 * Creates and returns a ValueModel that converts Floats to Integer, and vice versa.
	 * <p>
	 * 
	 * <strong>Constraints:</strong> The subject is of type <code>Float</code>. s
	 * 
	 * @param floatSubject
	 *            a Float ValueModel
	 * 
	 * @return a ValueModel that converts Floats to Integer
	 * 
	 * @throws NullPointerException
	 *             if the subject is {@code null}
	 */
	public ValueModel createFloatToIntegerConverter(ValueModel floatSubject)
	{
		return createFloatToIntegerConverter(floatSubject, 1);
	}

	/**
	 * Creates and returns a ValueModel that converts Floats to Integer, and vice versa. The multiplier can be used to convert Floats to percent, permill, etc.
	 * For a percentage, set the multiplier to be 100, for a permill, set the multiplier to be 1000.
	 * <p>
	 * 
	 * <strong>Constraints:</strong> The subject is of type <code>Float</code>.
	 * 
	 * @param floatSubject
	 *            a Float ValueModel
	 * @param multiplier
	 *            the multiplier used to convert the Float to Integer
	 * 
	 * @return a ValueModel that converts Floats to Integer
	 * 
	 * @throws NullPointerException
	 *             if the subject is {@code null}
	 */
	public ValueModel createFloatToIntegerConverter(ValueModel floatSubject, int multiplier)
	{
		return new FloatToIntegerConverter(changeSupportFactory, floatSubject, multiplier);
	}

	/**
	 * Creates and returns a ValueModel that converts Integers using the specified multiplier.
	 * <p>
	 * 
	 * Examples: multiplier=100, Integer(3) -> Integer(300), multiplier=1000, Integer(3) -> Integer(3000)
	 * <p>
	 * 
	 * <strong>Constraints:</strong> The subject is of type <code>Integer</code>.
	 * 
	 * @param integerSubject
	 *            a Integer ValueModel
	 * @param multiplier
	 *            the multiplier used for the conversion
	 * 
	 * @return a ValueModel that converts Integers using the specified multiplier
	 * 
	 * @throws NullPointerException
	 *             if the subject is {@code null}
	 * 
	 * @since 1.0.2
	 */
	public ValueModel createIntegerConverter(ValueModel integerSubject, double multiplier)
	{
		return new IntegerConverter(changeSupportFactory, integerSubject, multiplier);
	}

	/**
	 * Creates and returns a ValueModel that converts Long using the specified multiplier.
	 * <p>
	 * 
	 * Examples: multiplier=100, Long(3) -> Long(300), multiplier=1000, Long(3) -> Long(3000)
	 * <p>
	 * 
	 * <strong>Constraints:</strong> The subject is of type <code>Long</code>.
	 * 
	 * @param longSubject
	 *            a Long ValueModel
	 * @param multiplier
	 *            the multiplier used for the conversion
	 * 
	 * @return a ValueModel that converts Longs using the specified multiplier
	 * 
	 * @throws NullPointerException
	 *             if the subject is {@code null}
	 * 
	 * @since 1.0.2
	 */
	public ValueModel createLongConverter(ValueModel longSubject, double multiplier)
	{
		return new LongConverter(changeSupportFactory, longSubject, multiplier);
	}

	/**
	 * Creates and returns a ValueModel that converts Longs to Integer and vice versa.
	 * <p>
	 * 
	 * <strong>Constraints:</strong> The subject is of type <code>Long</code>, values written to the converter are of type <code>Integer</code>.
	 * 
	 * @param longSubject
	 *            a Long ValueModel
	 * @return a ValueModel that converts Longs to Integer
	 * 
	 * @throws NullPointerException
	 *             if the subject is {@code null}
	 */
	public ValueModel createLongToIntegerConverter(ValueModel longSubject)
	{
		return createLongToIntegerConverter(longSubject, 1);
	}

	/**
	 * Creates and returns a ValueModel that converts Longs to Integer and vice versa.
	 * <p>
	 * 
	 * <strong>Constraints:</strong> The subject is of type <code>Long</code>, values written to the converter are of type <code>Integer</code>.
	 * 
	 * @param longSubject
	 *            a Long ValueModel
	 * @param multiplier
	 *            used to multiply the Long when converting to Integer
	 * @return a ValueModel that converts Longs to Integer
	 * 
	 * @throws NullPointerException
	 *             if the subject is {@code null}
	 */
	public ValueModel createLongToIntegerConverter(ValueModel longSubject, int multiplier)
	{
		return new LongToIntegerConverter(changeSupportFactory, longSubject, multiplier);
	}

	/**
	 * Creates and returns a ValueModel that converts objects to Strings and vice versa. The conversion is performed by a <code>Format</code>.
	 * <p>
	 * 
	 * <strong>Constraints:</strong> The subject is of type <code>Object</code>; it must be formattable and parsable via the given <code>Format</code>.
	 * 
	 * @param subject
	 *            the underlying ValueModel.
	 * @param format
	 *            the <code>Format</code> used to format and parse
	 * 
	 * @return a ValueModel that converts objects to Strings and vice versa
	 * 
	 * @throws NullPointerException
	 *             if the subject or the format is {@code null}
	 */
	public ValueModel createStringConverter(ValueModel subject, Format format)
	{
		return new StringConverter(changeSupportFactory, subject, format);
	}

	/**
	 * Negates Booleans leaving null unchanged. Maps Boolean.TRUE to Boolean.FALSE, Boolean.FALSE to Boolean.TRUE, and null to null.
	 */
	public static final class BooleanNegator extends AbstractConverter
	{
		BooleanNegator(ChangeSupportFactory changeSupportFactory, ValueModel booleanSubject)
		{
			super(changeSupportFactory, booleanSubject);
		}

		/**
		 * Negates Booleans leaving null unchanged. Maps Boolean.TRUE to Boolean.FALSE, Boolean.FALSE to Boolean.TRUE, and null to null.
		 * 
		 * @param subjectValue
		 *            the subject value to invert
		 * @return the text that represents the subject value
		 * 
		 * @throws ClassCastException
		 *             if the subject's value is not a Boolean
		 */
		@Override
		public Object convertFromSubject(Object subjectValue)
		{
			return negate(subjectValue);
		}

		/**
		 * Inverts the given Boolean and sets it as the subject's new value.
		 * 
		 * @param newValue
		 *            the value to be inverted and set as new subject value
		 * @throws ClassCastException
		 *             if the new value is not a Boolean
		 * @throws IllegalArgumentException
		 *             if the new value does neither match the trueText nor the falseText
		 */
		public void setValue(Object newValue)
		{
			subject.setValue(negate(newValue));
		}

		/**
		 * Negates Booleans leaving null unchanged. Maps Boolean.TRUE to Boolean.FALSE , Boolean.FALSE to Boolean.TRUE, and null to null.
		 * 
		 * @param value
		 *            the value to invert
		 * @return the inverted Boolean value, or null if value is null
		 * 
		 * @throws ClassCastException
		 *             if the value is not a Boolean
		 */
		private Boolean negate(Object value)
		{
			if (value == null)
				return null;
			else if (Boolean.TRUE.equals(value))
				return Boolean.FALSE;
			else if (Boolean.FALSE.equals(value))
				return Boolean.TRUE;
			else
				throw new ClassCastException("The value must be a Boolean.");
		}

	}

	/**
	 * Converts Booleans to Strings and vice-versa using given texts for true, false, and null. Throws a ClassCastException if the value to convert is not a
	 * Boolean, or not a String for the reverse conversion.
	 */
	public static final class BooleanToStringConverter extends AbstractConverter
	{

		private final String trueText;
		private final String falseText;
		private final String nullText;

		BooleanToStringConverter(ChangeSupportFactory changeSupportFactory, ValueModel booleanSubject, String trueText, String falseText, String nullText)
		{
			super(changeSupportFactory, booleanSubject);
			if ((trueText == null) || (falseText == null) || (nullText == null))
			{
				throw new NullPointerException("The trueText, falseText and nullText must not be null.");
			}
			if (trueText.equals(falseText))
			{
				throw new IllegalArgumentException("The trueText and falseText must be different.");
			}
			this.trueText = trueText;
			this.falseText = falseText;
			this.nullText = nullText;
		}

		/**
		 * Converts the subject value to associated text representation. Rejects non-Boolean values.
		 * 
		 * @param subjectValue
		 *            the subject's new value
		 * @return the text that represents the subject value
		 * 
		 * @throws ClassCastException
		 *             if the subject's value is not a Boolean
		 */
		@Override
		public Object convertFromSubject(Object subjectValue)
		{
			if (Boolean.TRUE.equals(subjectValue))
				return trueText;
			else if (Boolean.FALSE.equals(subjectValue))
				return falseText;
			else if (subjectValue == null)
				return nullText;
			else
				throw new ClassCastException("The subject value must be of type Boolean.");
		}

		/**
		 * Converts the given String and sets the associated Boolean as the subject's new value. In case the new value equals neither this class' trueText, nor
		 * the falseText, nor the nullText, an IllegalArgumentException is thrown.
		 * 
		 * @param newValue
		 *            the value to be converted and set as new subject value
		 * @throws ClassCastException
		 *             if the new value is not a String
		 * @throws IllegalArgumentException
		 *             if the new value does neither match the trueText nor the falseText nor the nullText
		 */
		public void setValue(Object newValue)
		{
			if (!(newValue instanceof String))
				throw new ClassCastException("The new value must be a string.");

			String newString = (String) newValue;
			if (trueText.equalsIgnoreCase(newString))
			{
				subject.setValue(Boolean.TRUE);
			}
			else if (falseText.equalsIgnoreCase(newString))
			{
				subject.setValue(Boolean.FALSE);
			}
			else if (nullText.equalsIgnoreCase(newString))
			{
				subject.setValue(null);
			}
			else
				throw new IllegalArgumentException("The new value must be one of: " + trueText + '/' + falseText + '/' + nullText);
		}

	}

	/**
	 * Converts Doubles using a given multiplier.
	 */
	public static final class DoubleConverter extends AbstractConverter
	{

		private final double multiplier;

		DoubleConverter(ChangeSupportFactory changeSupportFactory, ValueModel doubleSubject, double multiplier)
		{
			super(changeSupportFactory, doubleSubject);
			this.multiplier = multiplier;
		}

		/**
		 * Converts the subject's value and returns a corresponding <code>Double</code> using the multiplier.
		 * 
		 * @param subjectValue
		 *            the subject's value
		 * @return the converted subjectValue
		 * @throws ClassCastException
		 *             if the subject value is not of type <code>Double</code>
		 */
		@Override
		public Object convertFromSubject(Object subjectValue)
		{
			double doubleValue = ((Double) subjectValue).doubleValue();
			return Double.valueOf(doubleValue * multiplier);
		}

		/**
		 * Converts a <code>Double</code> using the multiplier and sets it as new value.
		 * 
		 * @param newValue
		 *            the <code>Double</code> object that shall be converted
		 * @throws ClassCastException
		 *             if the new value is not of type <code>Double</code>
		 */
		public void setValue(Object newValue)
		{
			double doubleValue = ((Double) newValue).doubleValue();
			subject.setValue(Double.valueOf(doubleValue / multiplier));
		}

	}

	/**
	 * Converts Doubles to Integers and vice-versa.
	 */
	public static final class DoubleToIntegerConverter extends AbstractConverter
	{

		private final int multiplier;

		DoubleToIntegerConverter(ChangeSupportFactory changeSupportFactory, ValueModel doubleSubject, int multiplier)
		{
			super(changeSupportFactory, doubleSubject);
			this.multiplier = multiplier;
		}

		/**
		 * Converts the subject's value and returns a corresponding <code>Integer</code> value using the multiplier.
		 * 
		 * @param subjectValue
		 *            the subject's value
		 * @return the converted subjectValue
		 * @throws ClassCastException
		 *             if the subject value is not of type <code>Double</code>
		 */
		@Override
		public Object convertFromSubject(Object subjectValue)
		{
			double doubleValue = ((Double) subjectValue).doubleValue();
			if (multiplier != 1)
				doubleValue *= multiplier;
			return Integer.valueOf((int) Math.round(doubleValue));
		}

		/**
		 * Converts a <code>Double</code> using the multiplier and sets it as new value.
		 * 
		 * @param newValue
		 *            the <code>Integer</code> object that shall be converted
		 * @throws ClassCastException
		 *             if the new value is not of type <code>Integer</code>
		 */
		public void setValue(Object newValue)
		{
			double doubleValue = ((Integer) newValue).doubleValue();
			if (multiplier != 1)
				doubleValue /= multiplier;
			subject.setValue(Double.valueOf(doubleValue));
		}

	}

	/**
	 * Converts Floats using a given multiplier.
	 */
	public static final class FloatConverter extends AbstractConverter
	{

		private final float multiplier;

		FloatConverter(ChangeSupportFactory changeSupportFactory, ValueModel floatSubject, float multiplier)
		{
			super(changeSupportFactory, floatSubject);
			this.multiplier = multiplier;
		}

		/**
		 * Converts the subject's value and returns a corresponding <code>Float</code> using the multiplier.
		 * 
		 * @param subjectValue
		 *            the subject's value
		 * @return the converted subjectValue
		 * @throws ClassCastException
		 *             if the subject value is not of type <code>Float</code>
		 */
		@Override
		public Object convertFromSubject(Object subjectValue)
		{
			float floatValue = ((Float) subjectValue).floatValue();
			return Float.valueOf(floatValue * multiplier);
		}

		/**
		 * Converts a <code>Float</code> using the multiplier and sets it as new value.
		 * 
		 * @param newValue
		 *            the <code>Float</code> object that shall be converted
		 * @throws ClassCastException
		 *             if the new value is not of type <code>Float</code>
		 */
		public void setValue(Object newValue)
		{
			float floatValue = ((Float) newValue).floatValue();
			subject.setValue(Float.valueOf(floatValue / multiplier));
		}

	}

	/**
	 * Converts Floats to Integers and vice-versa.
	 */
	public static final class FloatToIntegerConverter extends AbstractConverter
	{

		private final int multiplier;

		FloatToIntegerConverter(ChangeSupportFactory changeSupportFactory, ValueModel floatSubject, int multiplier)
		{
			super(changeSupportFactory, floatSubject);
			this.multiplier = multiplier;
		}

		/**
		 * Converts the subject's value and returns a corresponding <code>Integer</code> using the multiplier.
		 * 
		 * @param subjectValue
		 *            the subject's value
		 * @return the converted subjectValue
		 * @throws ClassCastException
		 *             if the subject value is not of type <code>Float</code>
		 */
		@Override
		public Object convertFromSubject(Object subjectValue)
		{
			float floatValue = ((Float) subjectValue).floatValue();
			if (multiplier != 1)
				floatValue *= multiplier;
			return Integer.valueOf(Math.round(floatValue));
		}

		/**
		 * Converts a <code>Float</code> using the multiplier and sets it as new value.
		 * 
		 * @param newValue
		 *            the <code>Integer</code> object that shall be converted
		 * @throws ClassCastException
		 *             if the new value is not of type <code>Integer</code>
		 */
		public void setValue(Object newValue)
		{
			float floatValue = ((Integer) newValue).floatValue();
			if (multiplier != 1)
				floatValue /= multiplier;
			subject.setValue(Float.valueOf(floatValue));
		}

	}

	/**
	 * Converts Longs using a given multiplier.
	 */
	public static final class LongConverter extends AbstractConverter
	{

		private final double multiplier;

		LongConverter(ChangeSupportFactory changeSupportFactory, ValueModel longSubject, double multiplier)
		{
			super(changeSupportFactory, longSubject);
			this.multiplier = multiplier;
		}

		/**
		 * Converts the subject's value and returns a corresponding <code>Long</code> using the multiplier.
		 * 
		 * @param subjectValue
		 *            the subject's value
		 * @return the converted subjectValue
		 * @throws ClassCastException
		 *             if the subject value is not of type <code>Long</code>
		 */
		@Override
		public Object convertFromSubject(Object subjectValue)
		{
			double doubleValue = ((Long) subjectValue).doubleValue();
			return Long.valueOf((long) (doubleValue * multiplier));
		}

		/**
		 * Converts a <code>Long</code> using the multiplier and sets it as new value.
		 * 
		 * @param newValue
		 *            the <code>Long</code> object that shall be converted
		 * @throws ClassCastException
		 *             if the new value is not of type <code>Long</code>
		 */
		public void setValue(Object newValue)
		{
			double doubleValue = ((Long) newValue).doubleValue();
			subject.setValue(Long.valueOf((long) (doubleValue / multiplier)));
		}

	}

	/**
	 * Converts Integers using a given multiplier.
	 */
	public static final class IntegerConverter extends AbstractConverter
	{

		private final double multiplier;

		IntegerConverter(ChangeSupportFactory changeSupportFactory, ValueModel integerSubject, double multiplier)
		{
			super(changeSupportFactory, integerSubject);
			this.multiplier = multiplier;
		}

		/**
		 * Converts the subject's value and returns a corresponding <code>Integer</code> using the multiplier.
		 * 
		 * @param subjectValue
		 *            the subject's value
		 * @return the converted subjectValue
		 * @throws ClassCastException
		 *             if the subject value is not of type <code>Integer</code>
		 */
		@Override
		public Object convertFromSubject(Object subjectValue)
		{
			double doubleValue = ((Integer) subjectValue).doubleValue();
			return Integer.valueOf((int) (doubleValue * multiplier));
		}

		/**
		 * Converts a <code>Integer</code> using the multiplier and sets it as new value.
		 * 
		 * @param newValue
		 *            the <code>Integer</code> object that shall be converted
		 * @throws ClassCastException
		 *             if the new value is not of type <code>Integer</code>
		 */
		public void setValue(Object newValue)
		{
			double doubleValue = ((Integer) newValue).doubleValue();
			subject.setValue(Integer.valueOf((int) (doubleValue / multiplier)));
		}

	}

	/**
	 * Converts Longs to Integers and vice-versa.
	 */
	public static final class LongToIntegerConverter extends AbstractConverter
	{

		private final int multiplier;

		LongToIntegerConverter(ChangeSupportFactory changeSupportFactory, ValueModel longSubject, int multiplier)
		{
			super(changeSupportFactory, longSubject);
			this.multiplier = multiplier;
		}

		/**
		 * Converts the subject's value and returns a corresponding <code>Integer</code>.
		 * 
		 * @param subjectValue
		 *            the subject's value
		 * @return the converted subjectValue
		 * @throws ClassCastException
		 *             if the subject value is not of type <code>Float</code>
		 */
		@Override
		public Object convertFromSubject(Object subjectValue)
		{
			int intValue = ((Long) subjectValue).intValue();
			if (multiplier != 1)
				intValue *= multiplier;
			return Integer.valueOf(intValue);
		}

		/**
		 * Converts an Integer to Long and sets it as new value.
		 * 
		 * @param newValue
		 *            the <code>Integer</code> object that represents the percent value
		 * @throws ClassCastException
		 *             if the new value is not of type <code>Integer</code>
		 */
		public void setValue(Object newValue)
		{
			long longValue = ((Integer) newValue).longValue();
			if (multiplier != 1)
				longValue /= multiplier;
			subject.setValue(Long.valueOf(longValue));
		}

	}

	/**
	 * Converts Values to Strings and vice-versa using a given Format.
	 */
	public static final class StringConverter extends AbstractConverter
	{

		/**
		 * Holds the <code>Format</code> used to format and parse.
		 */
		private final Format format;

		/**
		 * Constructs a <code>StringConverter</code> on the given subject using the specified <code>Format</code>.
		 * 
		 * @param subject
		 *            the underlying ValueModel.
		 * @param format
		 *            the <code>Format</code> used to format and parse
		 * @throws NullPointerException
		 *             if the subject or the format is null.
		 */
		StringConverter(ChangeSupportFactory changeSupportFactory, ValueModel subject, Format format)
		{
			super(changeSupportFactory, subject);
			if (format == null)
			{
				throw new NullPointerException("The format must not be null.");
			}
			this.format = format;
		}

		/**
		 * Formats the subject value and returns a String representation.
		 * 
		 * @param subjectValue
		 *            the subject's value
		 * @return the formatted subjectValue
		 */
		@Override
		public Object convertFromSubject(Object subjectValue)
		{
			return format.format(subjectValue);
		}

		/**
		 * Parses the given String encoding and sets it as the subject's new value. Silently catches <code>ParseException</code>.
		 * 
		 * @param value
		 *            the value to be converted and set as new subject value
		 */
		public void setValue(Object value)
		{
			try
			{
				subject.setValue(format.parseObject((String) value));
			}
			catch (ParseException e)
			{
				// Do not change the subject value
			}
		}

	}

}
