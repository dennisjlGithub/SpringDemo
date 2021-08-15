package com.test.cxfwebservice.exception;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.test.cxfwebservice.vo.MessageVO;

public class EMMSException extends Exception {

	private static final long serialVersionUID = 1L;

	// 'cause' - is the underlying cause of the exception
	private Throwable cause;

	/**
	 * ArrayList containing MessageVO.  <BR>
	 * Each MessageVO consists of the 
	 * (1) 'bundle' - is refer to the Struts Resource Bundle
	 * (2) 'errorkey' is the key defined inside the bundle
	 * (3) 'args' is the substitable error string used by the bundle
	 * 
	 */
	private List<MessageVO> warningMessages = null;
    private List<MessageVO> errorMessages = null;
    
    private boolean confirm = false;
    
    private int MAX_NESTED_CAUSE_LEVEL = 10;
    
	/**
	 * Default Constructor
     *  
	 */
	public EMMSException() {
		super();
	}

    /**
     * Re-Use Exception(message) For e.getMessage() purpose
	 */
	public EMMSException(String message) {
	    super(message);
	}
	
	/**
     * Constructs a new exception with the specified cause Constructor with this
     * signature Since: 1.4
	 */
	public EMMSException(Throwable cause) {
        super();
        this.cause = cause;
    }
	
	/**
     * Constructs a new exception with the specified message and cause 
     * 
	 */
    public EMMSException(String message, Throwable cause) {
        super(message);
        this.cause = cause;
    }	

	/**
	 * Use this instead of putting error message in ResultContext. We should use
	 * exception to stop execution of error encountered!!!
	 * 
	 * @param bundle -
	 *            bundle that indicate the resource bundle type
	 * @param errorkey -
	 *            error id defined in the bundle
	 */
	public EMMSException(String bundle, String errorkey) {		
		init(bundle, errorkey);
	}

    /**
	 * Use this instead of putting error message in ResultContext. We should use
	 * exception to stop execution of error encountered!!!
	 * 
	 * @param bundle -
	 *            bundle that indicate the resource bundle type
	 * @param errorkey -
	 *            error id defined in the bundle
	 * @param args -
	 *            arguments used for message substitution
	 */
	public EMMSException(String bundle, String errorkey, Object[] args) {
	    init(bundle, errorkey, args);
	}
	
	/**
     * @param bundle
     * @param errorkey
     * @param args
     */
    protected void init(String bundle, String errorkey, Object[] args) {
        MessageVO message = new MessageVO(bundle, errorkey, args);
		this.errorMessages = new ArrayList<MessageVO>();
		this.errorMessages.add(message);
    }

	/**
     * @param bundle
     * @param errorkey
     */
    protected void init(String bundle, String errorkey) {
        MessageVO message = new MessageVO(bundle, errorkey);
		this.errorMessages = new ArrayList<MessageVO>();
		this.errorMessages.add(message);
    }

    /**
	 * Use this meothod to store error messages (and warning messages if they happen to come with error messages. 
	 * in ResultContext. We should use exception to stop execution of error encountered!!!
	 * 
	 * @param errorMessages -
	 *            Array of MesssgeVO instances
	 * @param warningMessages  -
	 *            Array of MesssgeVO instances
	 * @author Andy Kam
	 */
	public EMMSException(List<MessageVO> errorMessages, List<MessageVO> warningMessages) {
		this.errorMessages = errorMessages;
		this.warningMessages = warningMessages;
	}

	/**
	 * This returns the "resource bundle args" from the error message added <br>
	 * It expects that only one error message is being added thru. addErrorMessage() method. <br>
	 * @return Returns the args.
	 */
	public Object[] getArgs() {
	    Object[] args = null;
	    if(this.errorMessages != null && !this.errorMessages.isEmpty()) {
	        MessageVO message = (MessageVO)this.errorMessages.get(0);
	        args = message.getArgs();	        
	    }
		return args;
	}

	/**
	 * This returns the "resource bundle" from the error message added <br>
	 * It expects that only one error message is being added thru. addErrorMessage() method. <br>
	 * @return Returns the bundle.
	 */
	public String getBundle() {
	    String bundle = null;
	    if(this.errorMessages != null && !this.errorMessages.isEmpty()) {
	        MessageVO message = (MessageVO)this.errorMessages.get(0);
	        bundle = message.getBundle(); 
	    }
		
		return bundle;
	}

	/**
	 * This returns the "resource error key" from the error message added <br>
	 * It expects that only one error message is being added thru. addErrorMessage() method. <br>
	 * @return Returns the errorkey.
	 */
	public String getErrorkey() {
	    String errorkey = null;
	    if(this.errorMessages != null && !this.errorMessages.isEmpty()) {
	        MessageVO message = (MessageVO)this.errorMessages.get(0);
	        errorkey = message.getKey();	        
	    }
	
		return errorkey;
	}

	/**
	 * @return Returns the cause.
	 */
	public Throwable getCause() {
        return this.cause;
	}

	/**
	 * @return Returns true if it has cause.
	 */	
	public boolean hasCause() {
        return (this.cause != null);
	}
	
	public void addWarningMessages(List<MessageVO> messages) {
        if (warningMessages == null) {
            warningMessages = new ArrayList<MessageVO>();
        }
        warningMessages.addAll(messages);
    }

	public void addErrorMessages(List<MessageVO> messages) {
        if (errorMessages == null) {
        	errorMessages = new ArrayList<MessageVO>();
        }
        errorMessages.addAll(messages);
    }

	public void addWarningMessage(String bundle, String messageKey, Object[] messageArgs) {
        if (warningMessages == null) {
            warningMessages = new ArrayList<MessageVO>();
        }
        MessageVO message = new MessageVO(bundle, messageKey, messageArgs);
        warningMessages.add(message);
    }
    
    public void addWarningMessage(String bundle, String messageKey, String paramBundle, Object[] paramKeyArgs) {
        if (warningMessages == null) {
            warningMessages = new ArrayList<MessageVO>();
        }
        MessageVO message = new MessageVO(bundle, messageKey, paramBundle, paramKeyArgs);
        warningMessages.add(message);
    }    

    public void addErrorMessage(String bundle, String messageKey, Object[] messageArgs) {
        if (errorMessages == null) {
            errorMessages = new ArrayList<MessageVO>();
        }
        MessageVO message = new MessageVO(bundle, messageKey, messageArgs);
        errorMessages.add(message);
    }
    
    public void addErrorMessage(String bundle, String messageKey, String paramBundle, Object[] paramKeyArgs) {
        if (errorMessages == null) {
            errorMessages = new ArrayList<MessageVO>();
        }
        MessageVO message = new MessageVO(bundle, messageKey, paramBundle, paramKeyArgs);
        errorMessages.add(message);
    }    
    
    public boolean hasError() {
        if (getErrors() != null && getErrors().size() > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean hasWarning() {
        if (getWarnings() != null && getWarnings().size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<MessageVO> getErrors() {
        return this.errorMessages;
    }
    
    public List<MessageVO> getWarnings() {
        return this.warningMessages;
    }
    
    /**
     * @return Returns the confirmationInd.
     */
    public boolean isConfirm() {
        return this.confirm;
    }

    /**
     * @param confirmationInd
     *            The confirmationInd to set.
     */
    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }
    
    /**
     * This method retrieves all exceptions in an array that gets thrown along
     * its escalation path .
	 * 
     * @return Throwable[], an array of exception which starts from the upperest
     *         level to the lowerest level of exception (leaf node, the utimate
     *         cause).
     */
    public Throwable[] getAllCauses() {
		List<Throwable> 	causes = new ArrayList<Throwable>();
		Throwable	innerCause = null;
		int			levelCount = 0;
		innerCause = this.cause;
	
		// try add the inner exception wrapped by the current
		// exception into the list.
		while (innerCause != null && levelCount++ < MAX_NESTED_CAUSE_LEVEL) {
			// add the inner exception to the list.
			causes.add(innerCause);
			
			if (innerCause instanceof EMMSException) {
				innerCause = ((EMMSException) innerCause).getCause();
			} else {
				innerCause = null;
			}
		}
        return (Throwable[]) causes.toArray(new Throwable[] {});
	}

    /**
     * This method retrieve the very first cause (exception) created. It
     * traverses down the linked list of inner exceptions all the way to the
     * leaf node and retrieve it.
     * 
     * @return Throwable, an exception at the root of entire inner exception
     *         link list. It returns null, when no inner exceptions are found.
	 */
	public Throwable getRootCause() {
		Throwable[] allcauses = null;
		
		allcauses = getAllCauses();
		
		if (allcauses != null && allcauses.length > 0) {
			return allcauses[allcauses.length - 1];
		} else {
			return null;
		}
	}

    /**
     * This method traverses the chained exceptions and print stack trace of
     * each exception encountered from top to bottom (root cause).
	 *
	 */
	public void printNestedStackTrace() {
		Throwable[] causes = null;
		causes = getAllCauses();
		
		for (int i = 0; i < causes.length; i++) {
			causes[i].printStackTrace();
		}
	}

    /**
     * This method traverses the chained exceptions and print stack trace of
     * each exception encountered from top to bottom (root cause) to the writer.
     * It works similar to Throwable.printStackTrace(PrintWriter).
	 *
	 */
	public void printNestedStackTrace(PrintWriter writer) {
		Throwable[] causes = null;
		causes = getAllCauses();
		
		for (int i = 0; i < causes.length; i++) {
			causes[i].printStackTrace(writer);
		}
	}

	/**
	 * This method returns the number of nested levels of inner exceptions.
	 * 
	 * @return int, the number of nested levels of inner exceptions.
	 */
	public int getNestedExceptionLevel() {
		return getAllCauses().length;
	}    
}
