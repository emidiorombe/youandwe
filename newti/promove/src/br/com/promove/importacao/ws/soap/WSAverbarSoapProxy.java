package br.com.promove.importacao.ws.soap;

public class WSAverbarSoapProxy implements br.com.promove.importacao.ws.soap.WSAverbarSoap {
  private String _endpoint = null;
  private br.com.promove.importacao.ws.soap.WSAverbarSoap wSAverbarSoap = null;
  
  public WSAverbarSoapProxy() {
    _initWSAverbarSoapProxy();
  }
  
  public WSAverbarSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initWSAverbarSoapProxy();
  }
  
  private void _initWSAverbarSoapProxy() {
    try {
      wSAverbarSoap = (new br.com.promove.importacao.ws.soap.WSAverbarLocator()).getWSAverbarSoap();
      if (wSAverbarSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)wSAverbarSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)wSAverbarSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (wSAverbarSoap != null)
      ((javax.xml.rpc.Stub)wSAverbarSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public br.com.promove.importacao.ws.soap.WSAverbarSoap getWSAverbarSoap() {
    if (wSAverbarSoap == null)
      _initWSAverbarSoapProxy();
    return wSAverbarSoap;
  }
  
  public void incluiCTRC(java.lang.String codigoCliente, java.lang.String assinaturaDigital, br.com.promove.importacao.ws.soap.CtrcAverbar ctrc, javax.xml.rpc.holders.BooleanHolder incluiCTRCResult, br.com.promove.importacao.ws.soap.RespostaErroHolder oRespostaErro) throws java.rmi.RemoteException{
    if (wSAverbarSoap == null)
      _initWSAverbarSoapProxy();
    wSAverbarSoap.incluiCTRC(codigoCliente, assinaturaDigital, ctrc, incluiCTRCResult, oRespostaErro);
  }
  
  
}