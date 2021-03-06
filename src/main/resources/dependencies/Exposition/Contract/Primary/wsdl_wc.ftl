<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<wsdl:definitions xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/" xmlns:ebmns="http://www.entel.cl/EBM/${service.name}/v${service.version}" xmlns:http="http://schemas.xmlsoap.org/wsdl/http/" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/" xmlns:tns="http://www.entel.cl/ESC/${service.name}/v${service.version}" targetNamespace="http://www.entel.cl/ESC/${service.name}/v${service.version}">
  <wsdl:types>
    <xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema">
      <xs:import namespace="http://www.entel.cl/EBM/${service.name}/v${service.version}" schemaLocation="${service.name}_v${service.version}_EBM.xsd"/>
    </xs:schema>
  </wsdl:types>
  <wsdl:message name="${service.name}_Message_In">
    <wsdl:part element="ebmns:${service.name}_REQ" name="In"/>
  </wsdl:message>
  <wsdl:message name="${service.name}_Message_Out">
    <wsdl:part element="ebmns:${service.name}_RSP" name="Out"/>
  </wsdl:message>
  <wsdl:message name="${service.name}_Message_Fault">
    <wsdl:part element="ebmns:${service.name}_FRSP" name="Fault"/>
  </wsdl:message>
  <wsdl:portType name="${service.name}_PortType">
    <wsdl:operation name="${service.name}">
      <wsdl:input message="tns:${service.name}_Message_In"/>
      <wsdl:output message="tns:${service.name}_Message_Out"/>
      <wsdl:fault message="tns:${service.name}_Message_Fault" name="DefaultFault"/>
    </wsdl:operation>
  </wsdl:portType>
  <wsdl:binding name="${service.name}_SOAPBinding" type="tns:${service.name}_PortType">
    <soap:binding style="document" transport="http://schemas.xmlsoap.org/soap/http"/>
    <wsdl:operation name="${service.name}">
      <soap:operation soapAction="http://www.entel.cl/EBM/${service.name}/v${service.version}"/>
      <wsdl:input>
        <soap:body use="literal"/>
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal"/>
      </wsdl:output>
      <wsdl:fault name="DefaultFault">
        <soap:fault name="DefaultFault" use="literal"/>
      </wsdl:fault>
    </wsdl:operation>
  </wsdl:binding>
  <wsdl:binding name="${service.name}_SOAP12Binding" type="tns:${service.name}_PortType">
    <soap12:binding style="document" transport="http://schemas.xmlsoap.org/soap/http"/>
    <wsdl:operation name="${service.name}">
      <soap12:operation soapAction="http://www.entel.cl/EBM/${service.name}/v${service.version}"/>
      <wsdl:input>
        <soap12:body use="literal"/>
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal"/>
      </wsdl:output>
      <wsdl:fault name="DefaultFault">
        <soap12:fault name="DefaultFault" use="literal"/>
      </wsdl:fault>
    </wsdl:operation>
  </wsdl:binding>
  <wsdl:service name="${service.name}_Service">
    <wsdl:port binding="tns:${service.name}_SOAPBinding" name="${service.name}_Port">
      <soap:address location="http://www.entel.cl/ES/11/${service.name}/v${service.version}"/>
    </wsdl:port>
    <wsdl:port binding="tns:${service.name}_SOAP12Binding" name="${service.name}_12Port">
      <soap12:address location="http://www.entel.cl/ES/${service.name}/v${service.version}"/>
    </wsdl:port>
  </wsdl:service>
</wsdl:definitions>
