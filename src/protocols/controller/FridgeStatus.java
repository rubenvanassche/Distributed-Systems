/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package protocols.controller;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
/** Information about a fridge if it is open and who is using it */
@org.apache.avro.specific.AvroGenerated
public class FridgeStatus extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 6124863313649288618L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"FridgeStatus\",\"namespace\":\"protocols.controller\",\"doc\":\"Information about a fridge if it is open and who is using it\",\"fields\":[{\"name\":\"id\",\"type\":\"int\"},{\"name\":\"opened\",\"type\":\"boolean\"},{\"name\":\"userid\",\"type\":\"int\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<FridgeStatus> ENCODER =
      new BinaryMessageEncoder<FridgeStatus>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<FridgeStatus> DECODER =
      new BinaryMessageDecoder<FridgeStatus>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<FridgeStatus> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<FridgeStatus> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<FridgeStatus>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this FridgeStatus to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a FridgeStatus from a ByteBuffer. */
  public static FridgeStatus fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

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
   * @param id The new value for id
   * @param opened The new value for opened
   * @param userid The new value for userid
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
   * @return The value of the 'id' field.
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
   * @return The value of the 'opened' field.
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
   * @return The value of the 'userid' field.
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

  /**
   * Creates a new FridgeStatus RecordBuilder.
   * @return A new FridgeStatus RecordBuilder
   */
  public static protocols.controller.FridgeStatus.Builder newBuilder() {
    return new protocols.controller.FridgeStatus.Builder();
  }

  /**
   * Creates a new FridgeStatus RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new FridgeStatus RecordBuilder
   */
  public static protocols.controller.FridgeStatus.Builder newBuilder(protocols.controller.FridgeStatus.Builder other) {
    return new protocols.controller.FridgeStatus.Builder(other);
  }

  /**
   * Creates a new FridgeStatus RecordBuilder by copying an existing FridgeStatus instance.
   * @param other The existing instance to copy.
   * @return A new FridgeStatus RecordBuilder
   */
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
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
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

    /**
     * Creates a Builder by copying an existing FridgeStatus instance
     * @param other The existing instance to copy.
     */
    private Builder(protocols.controller.FridgeStatus other) {
            super(SCHEMA$);
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

    /**
      * Gets the value of the 'id' field.
      * @return The value.
      */
    public java.lang.Integer getId() {
      return id;
    }

    /**
      * Sets the value of the 'id' field.
      * @param value The value of 'id'.
      * @return This builder.
      */
    public protocols.controller.FridgeStatus.Builder setId(int value) {
      validate(fields()[0], value);
      this.id = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'id' field has been set.
      * @return True if the 'id' field has been set, false otherwise.
      */
    public boolean hasId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'id' field.
      * @return This builder.
      */
    public protocols.controller.FridgeStatus.Builder clearId() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'opened' field.
      * @return The value.
      */
    public java.lang.Boolean getOpened() {
      return opened;
    }

    /**
      * Sets the value of the 'opened' field.
      * @param value The value of 'opened'.
      * @return This builder.
      */
    public protocols.controller.FridgeStatus.Builder setOpened(boolean value) {
      validate(fields()[1], value);
      this.opened = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'opened' field has been set.
      * @return True if the 'opened' field has been set, false otherwise.
      */
    public boolean hasOpened() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'opened' field.
      * @return This builder.
      */
    public protocols.controller.FridgeStatus.Builder clearOpened() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'userid' field.
      * @return The value.
      */
    public java.lang.Integer getUserid() {
      return userid;
    }

    /**
      * Sets the value of the 'userid' field.
      * @param value The value of 'userid'.
      * @return This builder.
      */
    public protocols.controller.FridgeStatus.Builder setUserid(int value) {
      validate(fields()[2], value);
      this.userid = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'userid' field has been set.
      * @return True if the 'userid' field has been set, false otherwise.
      */
    public boolean hasUserid() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'userid' field.
      * @return This builder.
      */
    public protocols.controller.FridgeStatus.Builder clearUserid() {
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FridgeStatus build() {
      try {
        FridgeStatus record = new FridgeStatus();
        record.id = fieldSetFlags()[0] ? this.id : (java.lang.Integer) defaultValue(fields()[0]);
        record.opened = fieldSetFlags()[1] ? this.opened : (java.lang.Boolean) defaultValue(fields()[1]);
        record.userid = fieldSetFlags()[2] ? this.userid : (java.lang.Integer) defaultValue(fields()[2]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<FridgeStatus>
    WRITER$ = (org.apache.avro.io.DatumWriter<FridgeStatus>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<FridgeStatus>
    READER$ = (org.apache.avro.io.DatumReader<FridgeStatus>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
