<?xml version="1.0" encoding="ISO-8859-1"?>
<StyledLayerDescriptor version="1.0.0"
                       xsi:schemaLocation="http://www.opengis.net/sld StyledLayerDescriptor.xsd"
                       xmlns="http://www.opengis.net/sld"
                       xmlns:ogc="http://www.opengis.net/ogc"
                       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

    <!-- a Named Layer is the basic building block of an SLD document -->
    <NamedLayer>
        <Name>meteo_measurements</Name>
        <UserStyle>
            <!-- Styles can have names, titles and abstracts -->
            <Title>SmarteEmission Meteo Measurements</Title>
            <Abstract>SmarteEmission Meteo Measurements style</Abstract>
            <IsDefault>1</IsDefault>
            <!-- FeatureTypeStyles describe how to render different features -->
            <!-- A FeatureTypeStyle for rendering points -->
            <FeatureTypeStyle>
                <Rule>
                    <ogc:Filter>
                        <ogc:PropertyIsEqualTo>
                            <ogc:PropertyName>name</ogc:PropertyName>
                            <ogc:Literal>temperature</ogc:Literal>
                        </ogc:PropertyIsEqualTo>
                    </ogc:Filter>
                    <PointSymbolizer>
                        <Graphic>
                            <Mark>
                                <WellKnownName>square</WellKnownName>
                                <Fill>
                                    <CssParameter name="fill">#cc0066</CssParameter>
                                    <CssParameter name="fill-opacity">1.0</CssParameter>
                                </Fill>
                                <Stroke>
                                    <CssParameter name="stroke">#660033</CssParameter>
                                    <CssParameter name="stroke-width">1</CssParameter>
                                    <CssParameter name="stroke-linecap">round</CssParameter>
                                </Stroke>
                            </Mark>
                            <Size>40</Size>
                        </Graphic>
                    </PointSymbolizer>

                    <TextSymbolizer>
                        <Label>
                            <ogc:Function name="strConcat">
                                <ogc:Function name="numberFormat">
                                    <ogc:Literal>##</ogc:Literal>
                                    <ogc:PropertyName>value</ogc:PropertyName>
                                </ogc:Function>
                                <ogc:Literal>&#176;</ogc:Literal>
                            </ogc:Function>
                        </Label>

                        <Font>
                            <CssParameter name="font-family">
                                <ogc:Literal>SansSerif</ogc:Literal>
                            </CssParameter>

                            <CssParameter name="font-size">
                                <ogc:Literal>14</ogc:Literal>
                            </CssParameter>

                            <CssParameter name="font-weight">
                                <ogc:Literal>bold</ogc:Literal>
                            </CssParameter>
                        </Font>
                        <LabelPlacement>
                            <PointPlacement>
                                <AnchorPoint>
                                    <AnchorPointX>0.55</AnchorPointX>
                                    <AnchorPointY>0.5</AnchorPointY>
                                </AnchorPoint>
                            </PointPlacement>
                        </LabelPlacement>

                        <Fill>
                            <CssParameter name="fill">#FFFFCC</CssParameter>
                        </Fill>

                    </TextSymbolizer>
                </Rule>
                <Rule>
                    <ogc:Filter>
                        <ogc:PropertyIsEqualTo>
                            <ogc:PropertyName>name</ogc:PropertyName>
                            <ogc:Literal>pressure</ogc:Literal>
                        </ogc:PropertyIsEqualTo>
                    </ogc:Filter>
                    <PointSymbolizer>
                        <Graphic>
                            <Mark>
                                <WellKnownName>square</WellKnownName>
                                <Fill>
                                    <CssParameter name="fill">#cc0026</CssParameter>
                                    <CssParameter name="fill-opacity">1.0</CssParameter>
                                </Fill>
                                <Stroke>
                                    <CssParameter name="stroke">#660033</CssParameter>
                                    <CssParameter name="stroke-width">1</CssParameter>
                                    <CssParameter name="stroke-linecap">round</CssParameter>
                                </Stroke>
                            </Mark>
                            <Size>60</Size>
                        </Graphic>
                    </PointSymbolizer>

                    <TextSymbolizer>
                        <Label>
                            <ogc:Function name="strConcat">
                                <ogc:Function name="numberFormat">
                                    <ogc:Literal>##</ogc:Literal>
                                    <ogc:PropertyName>value</ogc:PropertyName>
                                </ogc:Function>
                                <ogc:Literal></ogc:Literal>
                            </ogc:Function>
                        </Label>

                        <Font>
                            <CssParameter name="font-family">
                                <ogc:Literal>SansSerif</ogc:Literal>
                            </CssParameter>

                            <CssParameter name="font-size">
                                <ogc:Literal>12</ogc:Literal>
                            </CssParameter>

                            <CssParameter name="font-weight">
                                 <ogc:Literal>bold</ogc:Literal>
                             </CssParameter>
                        </Font>
                        <LabelPlacement>
                            <PointPlacement>
                                <AnchorPoint>
                                    <AnchorPointX>0.55</AnchorPointX>
                                    <AnchorPointY>0.5</AnchorPointY>
                                </AnchorPoint>
                            </PointPlacement>
                        </LabelPlacement>

                        <Fill>
                            <CssParameter name="fill">#FFFFCC</CssParameter>
                        </Fill>

                    </TextSymbolizer>
                </Rule>
                <Rule>
                    <ogc:Filter>
                        <ogc:PropertyIsEqualTo>
                            <ogc:PropertyName>name</ogc:PropertyName>
                            <ogc:Literal>humidity</ogc:Literal>
                        </ogc:PropertyIsEqualTo>
                    </ogc:Filter>
                    <PointSymbolizer>
                        <Graphic>
                            <Mark>
                                <WellKnownName>square</WellKnownName>
                                <Fill>
                                    <CssParameter name="fill">#cc0066</CssParameter>
                                    <CssParameter name="fill-opacity">1.0</CssParameter>
                                </Fill>
                                <Stroke>
                                    <CssParameter name="stroke">#660033</CssParameter>
                                    <CssParameter name="stroke-width">1</CssParameter>
                                    <CssParameter name="stroke-linecap">round</CssParameter>
                                </Stroke>
                            </Mark>
                            <Size>40</Size>
                        </Graphic>
                    </PointSymbolizer>

                    <TextSymbolizer>
                        <Label>
                            <ogc:Function name="strConcat">
                                <ogc:Function name="numberFormat">
                                    <ogc:Literal>##</ogc:Literal>
                                    <ogc:PropertyName>value</ogc:PropertyName>
                                </ogc:Function>
                                <ogc:Literal></ogc:Literal>
                            </ogc:Function>
                        </Label>

                        <Font>
                            <CssParameter name="font-family">
                                <ogc:Literal>SansSerif</ogc:Literal>
                            </CssParameter>

                            <CssParameter name="font-size">
                                <ogc:Literal>14</ogc:Literal>
                            </CssParameter>
                            <CssParameter name="font-weight">
                                <ogc:Literal>bold</ogc:Literal>
                            </CssParameter>

                        </Font>
                        <LabelPlacement>
                            <PointPlacement>
                                <AnchorPoint>
                                    <AnchorPointX>0.55</AnchorPointX>
                                    <AnchorPointY>0.5</AnchorPointY>
                                </AnchorPoint>
                            </PointPlacement>
                        </LabelPlacement>

                        <Fill>
                            <CssParameter name="fill">#FFFFCC</CssParameter>
                        </Fill>

                    </TextSymbolizer>
                </Rule>

            </FeatureTypeStyle>
        </UserStyle>
    </NamedLayer>
</StyledLayerDescriptor>
