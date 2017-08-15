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
/** Device info for the network */
@org.apache.avro.specific.AvroGenerated
public class Device extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -1401559592984893583L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Device\",\"namespace\":\"protocols.controller\",\"doc\":\"Device info for the network\",\"fields\":[{\"name\":\"id\",\"type\":\"int\"},{\"name\":\"ipadress\",\"type\":\"string\"},{\"name\":\"port\",\"type\":\"int\"},{\"name\":\"type\",\"type\":\"string\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<Device> ENCODER =
      new BinaryMessageEncoder<Device>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<Device> DECODER =
      new BinaryMessageDecoder<Device>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<Device> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<Device> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<Device>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this Device to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a Device from a ByteBuffer. */
  public static Device fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public int id;
  @Deprecated public java.lang.CharSequence ipadress;
  @Deprecated public int port;
  @Deprecated public java.lang.CharSequence type;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public Device() {}

  /**
   * All-args constructor.
   * @param id The new value for id
   * @param ipadress The new value for ipadress
   * @param port The new value for port
   * @param type The new value for type
   */
  public Device(java.lang.Integer id, java.lang.CharSequence ipadress, java.lang.Integer port, java.lang.CharSequence type) {
    this.id = id;
    this.ipadress = ipadress;
    this.port = port;
    this.type = type;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return id;
    case 1: return ipadress;
    case 2: return port;
    case 3: return type;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: id = (java.lang.Integer)value$; break;
    case 1: ipadress = (java.lang.CharSequence)value$; break;
    case 2: port = (java.lang.Integer)value$; break;
    case 3: type = (java.lang.CharSequence)value$; break;
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
   * Gets the value of the 'ipadress' field.
   * @return The value of the 'ipadress' field.
   */
  public java.lang.CharSequence getIpadress() {
    return ipadress;
  }

  /**
   * Sets the value of the 'ipadress' field.
   * @param value the value to set.
   */
  public void setIpadress(java.lang.CharSequence value) {
    this.ipadress = value;
  }

  /**
   * Gets the value of the 'port' field.
   * @return The value of the 'port' field.
   */
  public java.lang.Integer getPort() {
    return port;
  }

  /**
   * Sets the value of the 'port' field.
   * @param value the value to set.
   */
  public void setPort(java.lang.Integer value) {
    this.port = value;
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
   * Creates a new Device RecordBuilder.
   * @return A new Device RecordBuilder
   */
  public static protocols.controller.Device.Builder newBuilder() {
    return new protocols.controller.Device.Builder();
  }

  /**
   * Creates a new Device RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new Device RecordBuilder
   */
  public static protocols.controller.Device.Builder newBuilder(protocols.controller.Device.Builder other) {
    return new protocols.controller.Device.Builder(other);
  }

  /**
   * Creates a new Device RecordBuilder by copying an existing Device instance.
   * @param other The existing instance to copy.
   * @return A new Device RecordBuilder
   */
  public static protocols.controller.Device.Builder newBuilder(protocols.controller.Device other) {
    return new protocols.controller.Device.Builder(other);
  }

  /**
   * RecordBuilder for Device instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Device>
    implements org.apache.avro.data.RecordBuilder<Device> {

    private int id;
    private java.lang.CharSequence ipadress;
    private int port;
    private java.lang.CharSequence type;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(protocols.controller.Device.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.ipadress)) {
        this.ipadress = data().deepCopy(fields()[1].schema(), other.ipadress);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.port)) {
        this.port = data().deepCopy(fields()[2].schema(), other.port);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.type)) {
        this.type = data().deepCopy(fields()[3].schema(), other.type);
        fieldSetFlags()[3] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing Device instance
     * @param other The existing instance to copy.
     */
    private Builder(protocols.controller.Device other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.ipadress)) {
        this.ipadress = data().deepCopy(fields()[1].schema(), other.ipadress);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.port)) {
        this.port = data().deepCopy(fields()[2].schema(), other.port);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.type)) {
        this.type = data().deepCopy(fields()[3].schema(), other.type);
        fieldSetFlags()[3] = true;
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
    public protocols.controller.Device.Builder setId(int value) {
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
    public protocols.controller.Device.Builder clearId() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'ipadress' field.
      * @return The value.
      */
    public java.lang.CharSequence getIpadress() {
      return ipadress;
    }

    /**
      * Sets the value of the 'ipadress' field.
      * @param value The value of 'ipadress'.
      * @return This builder.
      */
    public protocols.controller.Device.Builder setIpadress(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.ipadress = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'ipadress' field has been set.
      * @return True if the 'ipadress' field has been set, false otherwise.
      */
    public boolean hasIpadress() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'ipadress' field.
      * @return This builder.
      */
    public protocols.controller.Device.Builder clearIpadress() {
      ipadress = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'port' field.
      * @return The value.
      */
    public java.lang.Integer getPort() {
      return port;
    }

    /**
      * Sets the value of the 'port' field.
      * @param value The value of 'port'.
      * @return This builder.
      */
    public protocols.controller.Device.Builder setPort(int value) {
      validate(fields()[2], value);
      this.port = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'port' field has been set.
      * @return True if the 'port' field has been set, false otherwise.
      */
    public boolean hasPort() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'port' field.
      * @return This builder.
      */
    public protocols.controller.Device.Builder clearPort() {
      fieldSetFlags()[2] = false;
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
    public protocols.controller.Device.Builder setType(java.lang.CharSequence value) {
      validate(fields()[3], value);
      this.type = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'type' field has been set.
      * @return True if the 'type' field has been set, false otherwise.
      */
    public boolean hasType() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'type' field.
      * @return This builder.
      */
    public protocols.controller.Device.Builder clearType() {
      type = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Device build() {
      try {
        Device record = new Device();
        record.id = fieldSetFlags()[0] ? this.id : (java.lang.Integer) defaultValue(fields()[0]);
        record.ipadress = fieldSetFlags()[1] ? this.ipadress : (java.lang.CharSequence) defaultValue(fields()[1]);
        record.port = fieldSetFlags()[2] ? this.port : (java.lang.Integer) defaultValue(fields()[2]);
        record.type = fieldSetFlags()[3] ? this.type : (java.lang.CharSequence) defaultValue(fields()[3]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<Device>
    WRITER$ = (org.apache.avro.io.DatumWriter<Device>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<Device>
    READER$ = (org.apache.avro.io.DatumReader<Device>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
