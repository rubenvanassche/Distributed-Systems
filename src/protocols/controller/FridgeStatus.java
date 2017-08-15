/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package protocols.controller;  
@SuppressWarnings("all")
/** Information about a fridge if it is open and who is using it */
@org.apache.avro.specific.AvroGenerated
public class FridgeStatus extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"FridgeStatus\",\"namespace\":\"protocols.controller\",\"doc\":\"Information about a fridge if it is open and who is using it\",\"fields\":[{\"name\":\"id\",\"type\":\"int\"},{\"name\":\"opened\",\"type\":\"boolean\"},{\"name\":\"userid\",\"type\":\"int\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public int id;
  @Deprecated public boolean opened;
  @Deprecated public int userid;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>. 
   */
  public FridgeStatus() {}

  /**
   * All-args constructor.
   */
  public FridgeStatus(java.lang.Integer id, java.lang.Boolean opened, java.lang.Integer userid) {
    this.id = id;
    this.opened = opened;
    this.userid = userid;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return id;
    case 1: return opened;
    case 2: return userid;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: id = (java.lang.Integer)value$; break;
    case 1: opened = (java.lang.Boolean)value$; break;
    case 2: userid = (java.lang.Integer)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'id' field.
   */
  public java.lang.Integer getId() {
    return id;
  }

  /**
   * Sets the value of the 'id' field.
   * @param value the value to set.
   */
  public void setId(java.lang.Integer value) {
    this.id = value;
  }

  /**
   * Gets the value of the 'opened' field.
   */
  public java.lang.Boolean getOpened() {
    return opened;
  }

  /**
   * Sets the value of the 'opened' field.
   * @param value the value to set.
   */
  public void setOpened(java.lang.Boolean value) {
    this.opened = value;
  }

  /**
   * Gets the value of the 'userid' field.
   */
  public java.lang.Integer getUserid() {
    return userid;
  }

  /**
   * Sets the value of the 'userid' field.
   * @param value the value to set.
   */
  public void setUserid(java.lang.Integer value) {
    this.userid = value;
  }

  /** Creates a new FridgeStatus RecordBuilder */
  public static protocols.controller.FridgeStatus.Builder newBuilder() {
    return new protocols.controller.FridgeStatus.Builder();
  }
  
  /** Creates a new FridgeStatus RecordBuilder by copying an existing Builder */
  public static protocols.controller.FridgeStatus.Builder newBuilder(protocols.controller.FridgeStatus.Builder other) {
    return new protocols.controller.FridgeStatus.Builder(other);
  }
  
  /** Creates a new FridgeStatus RecordBuilder by copying an existing FridgeStatus instance */
  public static protocols.controller.FridgeStatus.Builder newBuilder(protocols.controller.FridgeStatus other) {
    return new protocols.controller.FridgeStatus.Builder(other);
  }
  
  /**
   * RecordBuilder for FridgeStatus instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<FridgeStatus>
    implements org.apache.avro.data.RecordBuilder<FridgeStatus> {

    private int id;
    private boolean opened;
    private int userid;

    /** Creates a new Builder */
    private Builder() {
      super(protocols.controller.FridgeStatus.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(protocols.controller.FridgeStatus.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.opened)) {
        this.opened = data().deepCopy(fields()[1].schema(), other.opened);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.userid)) {
        this.userid = data().deepCopy(fields()[2].schema(), other.userid);
        fieldSetFlags()[2] = true;
      }
    }
    
    /** Creates a Builder by copying an existing FridgeStatus instance */
    private Builder(protocols.controller.FridgeStatus other) {
            super(protocols.controller.FridgeStatus.SCHEMA$);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.opened)) {
        this.opened = data().deepCopy(fields()[1].schema(), other.opened);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.userid)) {
        this.userid = data().deepCopy(fields()[2].schema(), other.userid);
        fieldSetFlags()[2] = true;
      }
    }

    /** Gets the value of the 'id' field */
    public java.lang.Integer getId() {
      return id;
    }
    
    /** Sets the value of the 'id' field */
    public protocols.controller.FridgeStatus.Builder setId(int value) {
      validate(fields()[0], value);
      this.id = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'id' field has been set */
    public boolean hasId() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'id' field */
    public protocols.controller.FridgeStatus.Builder clearId() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'opened' field */
    public java.lang.Boolean getOpened() {
      return opened;
    }
    
    /** Sets the value of the 'opened' field */
    public protocols.controller.FridgeStatus.Builder setOpened(boolean value) {
      validate(fields()[1], value);
      this.opened = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'opened' field has been set */
    public boolean hasOpened() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'opened' field */
    public protocols.controller.FridgeStatus.Builder clearOpened() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'userid' field */
    public java.lang.Integer getUserid() {
      return userid;
    }
    
    /** Sets the value of the 'userid' field */
    public protocols.controller.FridgeStatus.Builder setUserid(int value) {
      validate(fields()[2], value);
      this.userid = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'userid' field has been set */
    public boolean hasUserid() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'userid' field */
    public protocols.controller.FridgeStatus.Builder clearUserid() {
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    public FridgeStatus build() {
      try {
        FridgeStatus record = new FridgeStatus();
        record.id = fieldSetFlags()[0] ? this.id : (java.lang.Integer) defaultValue(fields()[0]);
        record.opened = fieldSetFlags()[1] ? this.opened : (java.lang.Boolean) defaultValue(fields()[1]);
        record.userid = fieldSetFlags()[2] ? this.userid : (java.lang.Integer) defaultValue(fields()[2]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}