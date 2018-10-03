<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<wsdl:definitions xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/" xmlns:ebmns="http://www.entel.cl/EBM/${context.service.name}/v${context.service.version}" xmlns:http="http://schemas.xmlsoap.org/wsdl/http/" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/" xmlns:tns="http://www.entel.cl/ESC/${context.service.name}/v${context.service.version}" targetNamespace="http://www.entel.cl/ESC/${context.service.name}/v${context.service.version}">
  <wsdl:types>
    <xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema">
      <xs:import namespace="http://www.entel.cl/EBM/${context.service.name}/v${context.service.version}" schemaLocation="${context.service.name}_v${context.service.version}_EBM.xsd"/>
    </xs:schema>
  </wsdl:types>
  <wsdl:message name="${context.service.name}_Message_In">
    <wsdl:part element="ebmns:${context.service.name}_REQ" name="In"/>
  </wsdl:message>
  <wsdl:message name="${context.service.name}_Message_Out">
    <wsdl:part element="ebmns:${context.service.name}_RSP" name="Out"/>
  </wsdl:message>
  <wsdl:message name="${context.service.name}_Message_Fault">
    <wsdl:part element="ebmns:${context.service.name}_FRSP" name="Fault"/>
  </wsdl:message>
  <wsdl:portType name="${context.service.name}_PortType">
    <wsdl:operation name="${context.service.name}">
      <wsdl:input message="tns:${context.service.name}_Message_In"/>
      <wsdl:output message="tns:${context.service.name}_Message_Out"/>
      <wsdl:fault message="tns:${context.service.name}_Message_Fault" name="DefaultFault"/>
    </wsdl:operation>
  </wsdl:portType>
  <wsdl:binding name="${context.service.name}_SOAPBinding" type="tns:${context.service.name}_PortType">
    <soap:binding style="document" transport="http://schemas.xmlsoap.org/soap/http"/>
    <wsdl:operation name="${context.service.name}">
      <soap:operation soapAction="http://www.entel.cl/EBM/${context.service.name}/v${context.service.version}"/>
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
  <wsdl:binding name="${context.service.name}_SOAP12Binding" type="tns:${context.service.name}_PortType">
    <soap12:binding style="document" transport="http://schemas.xmlsoap.org/soap/http"/>
    <wsdl:operation name="${context.service.name}">
      <soap12:operation soapAction="http://www.entel.cl/EBM/${context.service.name}/v${context.service.version}"/>
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
  <wsdl:service name="${context.service.name}_Service">
    <wsdl:port binding="tns:${context.service.name}_SOAPBinding" name="${context.service.name}_Port">
      <soap:address location="http://www.entel.cl/ES/11/${context.service.name}/v${context.service.version}"/>
    </wsdl:port>
    <wsdl:port binding="tns:${context.service.name}_SOAP12Binding" name="${context.service.name}_12Port">
      <soap12:address location="http://www.entel.cl/ES/${context.service.name}/v${context.service.version}"/>
    </wsdl:port>
  </wsdl:service>
</wsdl:definitions>
