<?xml version = '1.0' encoding = 'UTF-8'?>
<application xmlns:soa="http://www.oracle.com/soa/rest" xmlns:xsd="http://www.w3.org/2001/XMLSchema" 
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
              xmlns="http://wadl.dev.java.net/2009/02">
   <doc title="${context.service.name}">${context.service.name}</doc>
   <resources base="http://www.entel.cl/ES/JSON/${context.service.name}/v1">
         <resource path="/">
         <method name="POST" soa:name="${context.service.name}">
            <request>
               <representation mediaType="application/json"/>
            </request>
            <response status="200">
               <representation mediaType="application/json"/>
            </response>
            <response status="500">
               <representation mediaType="application/json"/>
            </response>
         </method>
      </resource>  
   </resources>
</application>