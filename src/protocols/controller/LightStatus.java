/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package protocols.controller;  
@SuppressWarnings("all")
/** Information about a light and if it's on/off */
@org.apache.avro.specific.AvroGenerated
public class LightStatus extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"LightStatus\",\"namespace\":\"protocols.controller\",\"doc\":\"Information about a light and if it's on/off\",\"fields\":[{\"name\":\"id\",\"type\":\"int\"},{\"name\":\"state\",\"type\":\"boolean\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public int id;
  @Deprecated public boolean state;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>. 
   */
  public LightStatus() {}

  /**
   * All-args constructor.
   */
  public LightStatus(java.lang.Integer id, java.lang.Boolean state) {
    this.id = id;
    this.state = state;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return id;
    case 1: return state;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: id = (java.lang.Integer)value$; break;
    case 1: state = (java.lang.Boolean)value$; break;
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
   * Gets the value of the 'state' field.
   */
  public java.lang.Boolean getState() {
    return state;
  }

  /**
   * Sets the value of the 'state' field.
   * @param value the value to set.
   */
  public void setState(java.lang.Boolean value) {
    this.state = value;
  }

  /** Creates a new LightStatus RecordBuilder */
  public static protocols.controller.LightStatus.Builder newBuilder() {
    return new protocols.controller.LightStatus.Builder();
  }
  
  /** Creates a new LightStatus RecordBuilder by copying an existing Builder */
  public static protocols.controller.LightStatus.Builder newBuilder(protocols.controller.LightStatus.Builder other) {
    return new protocols.controller.LightStatus.Builder(other);
  }
  
  /** Creates a new LightStatus RecordBuilder by copying an existing LightStatus instance */
  public static protocols.controller.LightStatus.Builder newBuilder(protocols.controller.LightStatus other) {
    return new protocols.controller.LightStatus.Builder(other);
  }
  
  /**
   * RecordBuilder for LightStatus instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<LightStatus>
    implements org.apache.avro.data.RecordBuilder<LightStatus> {

    private int id;
    private boolean state;

    /** Creates a new Builder */
    private Builder() {
      super(protocols.controller.LightStatus.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(protocols.controller.LightStatus.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.state)) {
        this.state = data().deepCopy(fields()[1].schema(), other.state);
        fieldSetFlags()[1] = true;
      }
    }
    
    /** Creates a Builder by copying an existing LightStatus instance */
    private Builder(protocols.controller.LightStatus other) {
            super(protocols.controller.LightStatus.SCHEMA$);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.state)) {
        this.state = data().deepCopy(fields()[1].schema(), other.state);
        fieldSetFlags()[1] = true;
      }
    }

    /** Gets the value of the 'id' field */
    public java.lang.Integer getId() {
      return id;
    }
    
    /** Sets the value of the 'id' field */
    public protocols.controller.LightStatus.Builder setId(int value) {
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
    public protocols.controller.LightStatus.Builder clearId() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'state' field */
    public java.lang.Boolean getState() {
      return state;
    }
    
    /** Sets the value of the 'state' field */
    public protocols.controller.LightStatus.Builder setState(boolean value) {
      validate(fields()[1], value);
      this.state = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'state' field has been set */
    public boolean hasState() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'state' field */
    public protocols.controller.LightStatus.Builder clearState() {
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    public LightStatus build() {
      try {
        LightStatus record = new LightStatus();
        record.id = fieldSetFlags()[0] ? this.id : (java.lang.Integer) defaultValue(fields()[0]);
        record.state = fieldSetFlags()[1] ? this.state : (java.lang.Boolean) defaultValue(fields()[1]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
