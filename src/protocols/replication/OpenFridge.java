/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package protocols.replication;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
/** Information about the fridges opened */
@org.apache.avro.specific.AvroGenerated
public class OpenFridge extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 6833132713781078726L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"OpenFridge\",\"namespace\":\"protocols.replication\",\"doc\":\"Information about the fridges opened\",\"fields\":[{\"name\":\"fridgeId\",\"type\":\"int\"},{\"name\":\"userId\",\"type\":\"int\"},{\"name\":\"open\",\"type\":\"boolean\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<OpenFridge> ENCODER =
      new BinaryMessageEncoder<OpenFridge>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<OpenFridge> DECODER =
      new BinaryMessageDecoder<OpenFridge>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<OpenFridge> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<OpenFridge> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<OpenFridge>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this OpenFridge to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a OpenFridge from a ByteBuffer. */
  public static OpenFridge fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public int fridgeId;
  @Deprecated public int userId;
  @Deprecated public boolean open;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public OpenFridge() {}

  /**
   * All-args constructor.
   * @param fridgeId The new value for fridgeId
   * @param userId The new value for userId
   * @param open The new value for open
   */
  public OpenFridge(java.lang.Integer fridgeId, java.lang.Integer userId, java.lang.Boolean open) {
    this.fridgeId = fridgeId;
    this.userId = userId;
    this.open = open;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return fridgeId;
    case 1: return userId;
    case 2: return open;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: fridgeId = (java.lang.Integer)value$; break;
    case 1: userId = (java.lang.Integer)value$; break;
    case 2: open = (java.lang.Boolean)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'fridgeId' field.
   * @return The value of the 'fridgeId' field.
   */
  public java.lang.Integer getFridgeId() {
    return fridgeId;
  }

  /**
   * Sets the value of the 'fridgeId' field.
   * @param value the value to set.
   */
  public void setFridgeId(java.lang.Integer value) {
    this.fridgeId = value;
  }

  /**
   * Gets the value of the 'userId' field.
   * @return The value of the 'userId' field.
   */
  public java.lang.Integer getUserId() {
    return userId;
  }

  /**
   * Sets the value of the 'userId' field.
   * @param value the value to set.
   */
  public void setUserId(java.lang.Integer value) {
    this.userId = value;
  }

  /**
   * Gets the value of the 'open' field.
   * @return The value of the 'open' field.
   */
  public java.lang.Boolean getOpen() {
    return open;
  }

  /**
   * Sets the value of the 'open' field.
   * @param value the value to set.
   */
  public void setOpen(java.lang.Boolean value) {
    this.open = value;
  }

  /**
   * Creates a new OpenFridge RecordBuilder.
   * @return A new OpenFridge RecordBuilder
   */
  public static protocols.replication.OpenFridge.Builder newBuilder() {
    return new protocols.replication.OpenFridge.Builder();
  }

  /**
   * Creates a new OpenFridge RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new OpenFridge RecordBuilder
   */
  public static protocols.replication.OpenFridge.Builder newBuilder(protocols.replication.OpenFridge.Builder other) {
    return new protocols.replication.OpenFridge.Builder(other);
  }

  /**
   * Creates a new OpenFridge RecordBuilder by copying an existing OpenFridge instance.
   * @param other The existing instance to copy.
   * @return A new OpenFridge RecordBuilder
   */
  public static protocols.replication.OpenFridge.Builder newBuilder(protocols.replication.OpenFridge other) {
    return new protocols.replication.OpenFridge.Builder(other);
  }

  /**
   * RecordBuilder for OpenFridge instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<OpenFridge>
    implements org.apache.avro.data.RecordBuilder<OpenFridge> {

    private int fridgeId;
    private int userId;
    private boolean open;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(protocols.replication.OpenFridge.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.fridgeId)) {
        this.fridgeId = data().deepCopy(fields()[0].schema(), other.fridgeId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.userId)) {
        this.userId = data().deepCopy(fields()[1].schema(), other.userId);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.open)) {
        this.open = data().deepCopy(fields()[2].schema(), other.open);
        fieldSetFlags()[2] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing OpenFridge instance
     * @param other The existing instance to copy.
     */
    private Builder(protocols.replication.OpenFridge other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.fridgeId)) {
        this.fridgeId = data().deepCopy(fields()[0].schema(), other.fridgeId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.userId)) {
        this.userId = data().deepCopy(fields()[1].schema(), other.userId);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.open)) {
        this.open = data().deepCopy(fields()[2].schema(), other.open);
        fieldSetFlags()[2] = true;
      }
    }

    /**
      * Gets the value of the 'fridgeId' field.
      * @return The value.
      */
    public java.lang.Integer getFridgeId() {
      return fridgeId;
    }

    /**
      * Sets the value of the 'fridgeId' field.
      * @param value The value of 'fridgeId'.
      * @return This builder.
      */
    public protocols.replication.OpenFridge.Builder setFridgeId(int value) {
      validate(fields()[0], value);
      this.fridgeId = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'fridgeId' field has been set.
      * @return True if the 'fridgeId' field has been set, false otherwise.
      */
    public boolean hasFridgeId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'fridgeId' field.
      * @return This builder.
      */
    public protocols.replication.OpenFridge.Builder clearFridgeId() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'userId' field.
      * @return The value.
      */
    public java.lang.Integer getUserId() {
      return userId;
    }

    /**
      * Sets the value of the 'userId' field.
      * @param value The value of 'userId'.
      * @return This builder.
      */
    public protocols.replication.OpenFridge.Builder setUserId(int value) {
      validate(fields()[1], value);
      this.userId = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'userId' field has been set.
      * @return True if the 'userId' field has been set, false otherwise.
      */
    public boolean hasUserId() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'userId' field.
      * @return This builder.
      */
    public protocols.replication.OpenFridge.Builder clearUserId() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'open' field.
      * @return The value.
      */
    public java.lang.Boolean getOpen() {
      return open;
    }

    /**
      * Sets the value of the 'open' field.
      * @param value The value of 'open'.
      * @return This builder.
      */
    public protocols.replication.OpenFridge.Builder setOpen(boolean value) {
      validate(fields()[2], value);
      this.open = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'open' field has been set.
      * @return True if the 'open' field has been set, false otherwise.
      */
    public boolean hasOpen() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'open' field.
      * @return This builder.
      */
    public protocols.replication.OpenFridge.Builder clearOpen() {
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public OpenFridge build() {
      try {
        OpenFridge record = new OpenFridge();
        record.fridgeId = fieldSetFlags()[0] ? this.fridgeId : (java.lang.Integer) defaultValue(fields()[0]);
        record.userId = fieldSetFlags()[1] ? this.userId : (java.lang.Integer) defaultValue(fields()[1]);
        record.open = fieldSetFlags()[2] ? this.open : (java.lang.Boolean) defaultValue(fields()[2]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<OpenFridge>
    WRITER$ = (org.apache.avro.io.DatumWriter<OpenFridge>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<OpenFridge>
    READER$ = (org.apache.avro.io.DatumReader<OpenFridge>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}