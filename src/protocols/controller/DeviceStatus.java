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
/** Information about a device and if it's online or not */
@org.apache.avro.specific.AvroGenerated
public class DeviceStatus extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 7591569001568129411L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"DeviceStatus\",\"namespace\":\"protocols.controller\",\"doc\":\"Information about a device and if it's online or not\",\"fields\":[{\"name\":\"id\",\"type\":\"int\"},{\"name\":\"online\",\"type\":\"boolean\"},{\"name\":\"type\",\"type\":\"string\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<DeviceStatus> ENCODER =
      new BinaryMessageEncoder<DeviceStatus>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<DeviceStatus> DECODER =
      new BinaryMessageDecoder<DeviceStatus>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<DeviceStatus> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<DeviceStatus> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<DeviceStatus>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this DeviceStatus to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a DeviceStatus from a ByteBuffer. */
  public static DeviceStatus fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public int id;
  @Deprecated public boolean online;
  @Deprecated public java.lang.CharSequence type;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public DeviceStatus() {}

  /**
   * All-args constructor.
   * @param id The new value for id
   * @param online The new value for online
   * @param type The new value for type
   */
  public DeviceStatus(java.lang.Integer id, java.lang.Boolean online, java.lang.CharSequence type) {
    this.id = id;
    this.online = online;
    this.type = type;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return id;
    case 1: return online;
    case 2: return type;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: id = (java.lang.Integer)value$; break;
    case 1: online = (java.lang.Boolean)value$; break;
    case 2: type = (java.lang.CharSequence)value$; break;
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
   * Gets the value of the 'online' field.
   * @return The value of the 'online' field.
   */
  public java.lang.Boolean getOnline() {
    return online;
  }

  /**
   * Sets the value of the 'online' field.
   * @param value the value to set.
   */
  public void setOnline(java.lang.Boolean value) {
    this.online = value;
  }

  /**
   * Gets the value of the 'type' field.
   * @return The value of the 'type' field.
   */
  public java.lang.CharSequence getType() {
    return type;
  }

  /**
   * Sets the value of the 'type' field.
   * @param value the value to set.
   */
  public void setType(java.lang.CharSequence value) {
    this.type = value;
  }

  /**
   * Creates a new DeviceStatus RecordBuilder.
   * @return A new DeviceStatus RecordBuilder
   */
  public static protocols.controller.DeviceStatus.Builder newBuilder() {
    return new protocols.controller.DeviceStatus.Builder();
  }

  /**
   * Creates a new DeviceStatus RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new DeviceStatus RecordBuilder
   */
  public static protocols.controller.DeviceStatus.Builder newBuilder(protocols.controller.DeviceStatus.Builder other) {
    return new protocols.controller.DeviceStatus.Builder(other);
  }

  /**
   * Creates a new DeviceStatus RecordBuilder by copying an existing DeviceStatus instance.
   * @param other The existing instance to copy.
   * @return A new DeviceStatus RecordBuilder
   */
  public static protocols.controller.DeviceStatus.Builder newBuilder(protocols.controller.DeviceStatus other) {
    return new protocols.controller.DeviceStatus.Builder(other);
  }

  /**
   * RecordBuilder for DeviceStatus instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<DeviceStatus>
    implements org.apache.avro.data.RecordBuilder<DeviceStatus> {

    private int id;
    private boolean online;
    private java.lang.CharSequence type;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(protocols.controller.DeviceStatus.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.online)) {
        this.online = data().deepCopy(fields()[1].schema(), other.online);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.type)) {
        this.type = data().deepCopy(fields()[2].schema(), other.type);
        fieldSetFlags()[2] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing DeviceStatus instance
     * @param other The existing instance to copy.
     */
    private Builder(protocols.controller.DeviceStatus other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.online)) {
        this.online = data().deepCopy(fields()[1].schema(), other.online);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.type)) {
        this.type = data().deepCopy(fields()[2].schema(), other.type);
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
    public protocols.controller.DeviceStatus.Builder setId(int value) {
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
    public protocols.controller.DeviceStatus.Builder clearId() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'online' field.
      * @return The value.
      */
    public java.lang.Boolean getOnline() {
      return online;
    }

    /**
      * Sets the value of the 'online' field.
      * @param value The value of 'online'.
      * @return This builder.
      */
    public protocols.controller.DeviceStatus.Builder setOnline(boolean value) {
      validate(fields()[1], value);
      this.online = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'online' field has been set.
      * @return True if the 'online' field has been set, false otherwise.
      */
    public boolean hasOnline() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'online' field.
      * @return This builder.
      */
    public protocols.controller.DeviceStatus.Builder clearOnline() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'type' field.
      * @return The value.
      */
    public java.lang.CharSequence getType() {
      return type;
    }

    /**
      * Sets the value of the 'type' field.
      * @param value The value of 'type'.
      * @return This builder.
      */
    public protocols.controller.DeviceStatus.Builder setType(java.lang.CharSequence value) {
      validate(fields()[2], value);
      this.type = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'type' field has been set.
      * @return True if the 'type' field has been set, false otherwise.
      */
    public boolean hasType() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'type' field.
      * @return This builder.
      */
    public protocols.controller.DeviceStatus.Builder clearType() {
      type = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public DeviceStatus build() {
      try {
        DeviceStatus record = new DeviceStatus();
        record.id = fieldSetFlags()[0] ? this.id : (java.lang.Integer) defaultValue(fields()[0]);
        record.online = fieldSetFlags()[1] ? this.online : (java.lang.Boolean) defaultValue(fields()[1]);
        record.type = fieldSetFlags()[2] ? this.type : (java.lang.CharSequence) defaultValue(fields()[2]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<DeviceStatus>
    WRITER$ = (org.apache.avro.io.DatumWriter<DeviceStatus>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<DeviceStatus>
    READER$ = (org.apache.avro.io.DatumReader<DeviceStatus>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
