package com.netappsid.binding.adapter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.PlainDocument;
import javax.swing.text.Position;
import javax.swing.text.Segment;

import com.jgoodies.binding.BindingUtils;
import com.jgoodies.binding.value.ValueModel;
import com.netappsid.validate.Validate;

public class DocumentAdapter implements Document
{
	private final ValueModel subject;
	private final Document delegate;
	private final SubjectValueChangeHandler subjectValueChangeHandler;
	private final DocumentChangeHandler documentChangeHandler;

	public DocumentAdapter(ValueModel subject)
	{
		this(subject, new PlainDocument(), false);
	}

	public DocumentAdapter(ValueModel subject, boolean filterNewlines)
	{
		this(subject, new PlainDocument(), filterNewlines);
	}

	public DocumentAdapter(ValueModel subject, Document document)
	{
		this(subject, document, false);
	}

	public DocumentAdapter(ValueModel subject, Document document, boolean filterNewlines)
	{
		this.subject = Validate.notNull(subject, "The subject must not be null.");
		this.delegate = Validate.notNull(document, "The document must not be null.");
		this.subjectValueChangeHandler = new SubjectValueChangeHandler();
		this.documentChangeHandler = new DocumentChangeHandler();

		this.subject.addValueChangeListener(subjectValueChangeHandler);

		putProperty("filterNewlines", Boolean.valueOf(filterNewlines));
		addDocumentListener(documentChangeHandler);
		setDocumentTextSilently(getSubjectText());
	}

	private String getDocumentText()
	{
		try
		{
			return delegate.getText(0, delegate.getLength());
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * Sets the document contents without notifying the subject of changes. Invoked by the subject change listener. Removes the existing text first, then
	 * inserts the new text; therefore a BadLocationException should not happen. In case the delegate is an <code>AbstractDocument</code> the text is replaced
	 * instead of a combined remove plus insert.
	 * 
	 * @param newText
	 *            the text to be set in the document
	 */
	private void setDocumentTextSilently(String newText)
	{
		delegate.removeDocumentListener(documentChangeHandler);

		try
		{
			if (delegate instanceof AbstractDocument)
			{
				((AbstractDocument) delegate).replace(0, delegate.getLength(), newText, null);
			}
			else
			{
				delegate.remove(0, delegate.getLength());
				delegate.insertString(0, newText, null);
			}
		}
		catch (BadLocationException e)
		{
			// Should not happen in the way we invoke #remove and #insertString
		}

		delegate.addDocumentListener(documentChangeHandler);
	}

	private String getSubjectText()
	{
		return subject.getValue() != null ? (String) subject.getValue() : "";
	}

	/**
	 * Sets the given text as new subject value. Since the subject may modify this text, we cannot update silently, i.e. we cannot remove and add the
	 * subjectValueChangeHandler before/after the update. Since this change is invoked during a Document write operation, the document is write-locked and so,
	 * we cannot modify the document before all document listeners have been notified about the change.
	 * <p>
	 * 
	 * Therefore we listen to subject changes and defer any document changes using <code>SwingUtilities.invokeLater</code>. This mode is activated by setting
	 * the subject change handler's <code>updateLater</code> to true.
	 * 
	 * @param newText
	 *            the text to be set in the subject
	 */
	private void setSubjectText(String newText)
	{
		subjectValueChangeHandler.setUpdateLater(true);
		subject.setValue(newText);
		subjectValueChangeHandler.setUpdateLater(false);
	}

	public int getLength()
	{
		return delegate.getLength();
	}

	public void addDocumentListener(DocumentListener listener)
	{
		delegate.addDocumentListener(listener);
	}

	public void removeDocumentListener(DocumentListener listener)
	{
		delegate.removeDocumentListener(listener);
	}

	public void addUndoableEditListener(UndoableEditListener listener)
	{
		delegate.addUndoableEditListener(listener);
	}

	public void removeUndoableEditListener(UndoableEditListener listener)
	{
		delegate.removeUndoableEditListener(listener);
	}

	public Object getProperty(Object key)
	{
		return delegate.getProperty(key);
	}

	public void putProperty(Object key, Object value)
	{
		delegate.putProperty(key, value);
	}

	public void remove(int offs, int len) throws BadLocationException
	{
		delegate.remove(offs, len);
	}

	public void insertString(int offset, String str, AttributeSet a) throws BadLocationException
	{
		delegate.insertString(offset, str, a);
	}

	public String getText(int offset, int length) throws BadLocationException
	{
		return delegate.getText(offset, length);
	}

	public void getText(int offset, int length, Segment txt) throws BadLocationException
	{
		delegate.getText(offset, length, txt);
	}

	public Position getStartPosition()
	{
		return delegate.getStartPosition();
	}

	public Position getEndPosition()
	{
		return delegate.getEndPosition();
	}

	public Position createPosition(int offs) throws BadLocationException
	{
		return delegate.createPosition(offs);
	}

	public Element[] getRootElements()
	{
		return delegate.getRootElements();
	}

	public Element getDefaultRootElement()
	{
		return delegate.getDefaultRootElement();
	}

	public void render(Runnable r)
	{
		delegate.render(r);
	}

	/**
	 * Handles changes in the subject value and updates this document - if necessary.
	 * <p>
	 * 
	 * Document changes update the subject text and result in a subject property change. Most of these changes will just reflect the former subject change.
	 * However, in some cases the subject may modify the text set, for example to ensure upper case characters. This method reduces the number of document
	 * updates by checking the old and new text. If the old and new text are equal or both null, this method does nothing.
	 * <p>
	 * 
	 * Since subject changes as a result of a document change may not modify the write-locked document immediately, we defer the update if necessary using
	 * <code>SwingUtilities.invokeLater</code>.
	 * <p>
	 * 
	 * See the DocumentAdapter's JavaDoc class comment for the limitations of the deferred document change.
	 */
	private class SubjectValueChangeHandler implements PropertyChangeListener
	{
		private boolean updateLater;

		void setUpdateLater(boolean updateLater)
		{
			this.updateLater = updateLater;
		}

		/**
		 * The subject value has changed; updates the document immediately or later - depending on the <code>updateLater</code> state.
		 * 
		 * @param evt
		 *            the event to handle
		 */
		public void propertyChange(PropertyChangeEvent evt)
		{
			final String newText = evt.getNewValue() == null ? getSubjectText() : (String) evt.getNewValue();
			
			if (BindingUtils.equals(getDocumentText(), newText))
			{
				return;
			}

			if (updateLater)
			{
				SwingUtilities.invokeLater(new Runnable()
					{
						public void run()
						{
							setDocumentTextSilently(newText);
						}
					});
			}
			else
			{
				setDocumentTextSilently(newText);
			}
		}
	}

	private class DocumentChangeHandler implements DocumentListener
	{
		public void insertUpdate(DocumentEvent e)
		{
			updateSubject();
		}

		public void removeUpdate(DocumentEvent e)
		{
			updateSubject();
		}

		public void changedUpdate(DocumentEvent e)
		{

		}
		
		private void updateSubject()
		{
			setSubjectText(getDocumentText());
		}
	}
}
