/**
 * WSAverbarLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.com.promove.importacao.ws.soap;

public class WSAverbarLocator extends org.apache.axis.client.Service implements br.com.promove.importacao.ws.soap.WSAverbar {

    public WSAverbarLocator() {
    }


    public WSAverbarLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSAverbarLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WSAverbarSoap
    private java.lang.String WSAverbarSoap_address = "http://xxxcnn0054.locaweb.com.br/WebServices/WSAverbar.asmx";

    public java.lang.String getWSAverbarSoapAddress() {
        return WSAverbarSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WSAverbarSoapWSDDServiceName = "WSAverbarSoap";

    public java.lang.String getWSAverbarSoapWSDDServiceName() {
        return WSAverbarSoapWSDDServiceName;
    }

    public void setWSAverbarSoapWSDDServiceName(java.lang.String name) {
        WSAverbarSoapWSDDServiceName = name;
    }

    public br.com.promove.importacao.ws.soap.WSAverbarSoap getWSAverbarSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSAverbarSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSAverbarSoap(endpoint);
    }

    public br.com.promove.importacao.ws.soap.WSAverbarSoap getWSAverbarSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            br.com.promove.importacao.ws.soap.WSAverbarSoapStub _stub = new br.com.promove.importacao.ws.soap.WSAverbarSoapStub(portAddress, this);
            _stub.setPortName(getWSAverbarSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSAverbarSoapEndpointAddress(java.lang.String address) {
        WSAverbarSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (br.com.promove.importacao.ws.soap.WSAverbarSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                br.com.promove.importacao.ws.soap.WSAverbarSoapStub _stub = new br.com.promove.importacao.ws.soap.WSAverbarSoapStub(new java.net.URL(WSAverbarSoap_address), this);
                _stub.setPortName(getWSAverbarSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("WSAverbarSoap".equals(inputPortName)) {
            return getWSAverbarSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://xxxcnn0054.locaweb.com.br/WebServices", "WSAverbar");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://xxxcnn0054.locaweb.com.br/WebServices", "WSAverbarSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WSAverbarSoap".equals(portName)) {
            setWSAverbarSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
