package com.test.cxfwebservice.vo;

public class MessageVO extends AbstractBaseObject {
    
    private String key = null;
    private String bundle = null;
    private String paramBundle = null;
    private Object[] args = null;
    
    public MessageVO() {
        super();
    }
 
    /** 
     * This constructor is used for the message without args (or value substitution) <br>
     * @param bundle - the bundle to be used for error/message resource
     * @param key - key to indiciate the message
     */
    public MessageVO(String bundle, String key) {
        this.bundle = bundle;
        this.key = key;        
    }
    
    public MessageVO(String bundle, String key, Object[] args) {
        super();
        this.bundle = bundle;
        this.key = key;        
        this.args = args;
    }
    
    public MessageVO(String bundle, String key, String paramBundle, Object[] args) {
        super();
        this.bundle = bundle;
        this.paramBundle = paramBundle;
        this.key = key;        
        this.args = args;
    }    
    
    /**
     * @return Returns the args.
     */
    public Object[] getArgs() {
        return args;
    }
    /**
     * @param args The args to set.
     */
    public void setArgs(Object[] args) {
        this.args = args;
    }
    /**
     * @return Returns the bundle.
     */
    public String getBundle() {
        return bundle;
    }
    /**
     * @param bundle The bundle to set.
     */
    public void setBundle(String bundle) {
        this.bundle = bundle;
    }
    /**
     * @return Returns the key.
     */
    public String getKey() {
        return key;
    }
    /**
     * @param key The key to set.
     */
    public void setKey(String key) {
        this.key = key;
    }
    
    /**
     * @return Returns the paramBundle.
     */
    public String getParamBundle() {
        return paramBundle;
    }
    /**
     * @param paramBundle The paramBundle to set.
     */
    public void setParamBundle(String paramBundle) {
        this.paramBundle = paramBundle;
    }    
}
