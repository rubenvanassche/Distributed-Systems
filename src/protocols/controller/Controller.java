/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package protocols.controller;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public interface Controller {
  public static final org.apache.avro.Protocol PROTOCOL = org.apache.avro.Protocol.parse("{\"protocol\":\"Controller\",\"namespace\":\"protocols.controller\",\"types\":[{\"type\":\"record\",\"name\":\"Device\",\"doc\":\"Device info for the network\",\"fields\":[{\"name\":\"id\",\"type\":\"int\"},{\"name\":\"ipadress\",\"type\":\"string\"},{\"name\":\"port\",\"type\":\"int\"},{\"name\":\"type\",\"type\":\"string\"}]},{\"type\":\"error\",\"name\":\"Failure\",\"doc\":\"Failure\",\"fields\":[{\"name\":\"message\",\"type\":\"string\"}]},{\"type\":\"record\",\"name\":\"LightStatus\",\"doc\":\"Information about a light and if it's on/off\",\"fields\":[{\"name\":\"id\",\"type\":\"int\"},{\"name\":\"state\",\"type\":\"boolean\"}]},{\"type\":\"record\",\"name\":\"DeviceStatus\",\"doc\":\"Information about a device and if it's online or not\",\"fields\":[{\"name\":\"id\",\"type\":\"int\"},{\"name\":\"online\",\"type\":\"boolean\"},{\"name\":\"type\",\"type\":\"string\"}]}],\"messages\":{\"register\":{\"request\":[{\"name\":\"device\",\"type\":\"Device\"}],\"response\":\"boolean\",\"errors\":[\"Failure\"]},\"updateTemperature\":{\"request\":[{\"name\":\"id\",\"type\":\"int\"},{\"name\":\"value\",\"type\":\"double\"}],\"response\":\"boolean\",\"errors\":[\"Failure\"]},\"userLeave\":{\"request\":[{\"name\":\"id\",\"type\":\"int\"}],\"response\":\"boolean\",\"errors\":[\"Failure\"]},\"userEnters\":{\"request\":[{\"name\":\"id\",\"type\":\"int\"}],\"response\":\"boolean\",\"errors\":[\"Failure\"]},\"getTemperature\":{\"request\":[],\"response\":\"double\",\"errors\":[\"Failure\"]},\"getTemperatureHistory\":{\"request\":[],\"response\":{\"type\":\"array\",\"items\":\"double\"},\"errors\":[\"Failure\"]},\"getDevices\":{\"request\":[],\"response\":{\"type\":\"array\",\"items\":\"DeviceStatus\"},\"errors\":[\"Failure\"]},\"getLights\":{\"request\":[],\"response\":{\"type\":\"array\",\"items\":\"LightStatus\"},\"errors\":[\"Failure\"]},\"turnLightOn\":{\"request\":[{\"name\":\"id\",\"type\":\"int\"}],\"response\":\"boolean\",\"errors\":[\"Failure\"]},\"turnLightOff\":{\"request\":[{\"name\":\"id\",\"type\":\"int\"}],\"response\":\"boolean\",\"errors\":[\"Failure\"]}}}");
  boolean register(protocols.controller.Device device) throws org.apache.avro.AvroRemoteException, protocols.controller.Failure;
  boolean updateTemperature(int id, double value) throws org.apache.avro.AvroRemoteException, protocols.controller.Failure;
  boolean userLeave(int id) throws org.apache.avro.AvroRemoteException, protocols.controller.Failure;
  boolean userEnters(int id) throws org.apache.avro.AvroRemoteException, protocols.controller.Failure;
  double getTemperature() throws org.apache.avro.AvroRemoteException, protocols.controller.Failure;
  java.util.List<java.lang.Double> getTemperatureHistory() throws org.apache.avro.AvroRemoteException, protocols.controller.Failure;
  java.util.List<protocols.controller.DeviceStatus> getDevices() throws org.apache.avro.AvroRemoteException, protocols.controller.Failure;
  java.util.List<protocols.controller.LightStatus> getLights() throws org.apache.avro.AvroRemoteException, protocols.controller.Failure;
  boolean turnLightOn(int id) throws org.apache.avro.AvroRemoteException, protocols.controller.Failure;
  boolean turnLightOff(int id) throws org.apache.avro.AvroRemoteException, protocols.controller.Failure;

  @SuppressWarnings("all")
  public interface Callback extends Controller {
    public static final org.apache.avro.Protocol PROTOCOL = protocols.controller.Controller.PROTOCOL;
    void register(protocols.controller.Device device, org.apache.avro.ipc.Callback<java.lang.Boolean> callback) throws java.io.IOException;
    void updateTemperature(int id, double value, org.apache.avro.ipc.Callback<java.lang.Boolean> callback) throws java.io.IOException;
    void userLeave(int id, org.apache.avro.ipc.Callback<java.lang.Boolean> callback) throws java.io.IOException;
    void userEnters(int id, org.apache.avro.ipc.Callback<java.lang.Boolean> callback) throws java.io.IOException;
    void getTemperature(org.apache.avro.ipc.Callback<java.lang.Double> callback) throws java.io.IOException;
    void getTemperatureHistory(org.apache.avro.ipc.Callback<java.util.List<java.lang.Double>> callback) throws java.io.IOException;
    void getDevices(org.apache.avro.ipc.Callback<java.util.List<protocols.controller.DeviceStatus>> callback) throws java.io.IOException;
    void getLights(org.apache.avro.ipc.Callback<java.util.List<protocols.controller.LightStatus>> callback) throws java.io.IOException;
    void turnLightOn(int id, org.apache.avro.ipc.Callback<java.lang.Boolean> callback) throws java.io.IOException;
    void turnLightOff(int id, org.apache.avro.ipc.Callback<java.lang.Boolean> callback) throws java.io.IOException;
  }
}