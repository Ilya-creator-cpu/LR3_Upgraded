package package1;


//import bin.ElementsBinder;
import bin.ElementsBinder;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.SneakyThrows;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParser;
import org.eclipse.rdf4j.rio.helpers.StatementCollector;
import org.junit.jupiter.api.Test;
import package2.CimToJavaConverter;
import package2.SldToCimConverter;
import package3.JsonMapper;
import org.eclipse.rdf4j.rio.Rio;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import package5.SparQL;
import static org.eclipse.rdf4j.rio.Rio.createParser;


public class TestClass {
    @Test @SneakyThrows
    public void test()  {


        /// создание объектов Java
        JsonMapper jsonMapper = new JsonMapper();

        SingleLineDiagram sld = jsonMapper.mapJsonToSld("src/test/resources/Viezdnoe.json");
        List<DeviceDirectory> dev =  jsonMapper.mapJsonToDev("src/test/resources/DeviceDirectory.json");
        List<VoltageLevelDirectory> vol =  jsonMapper.mapJsonToVol("src/test/resources/VoltageLevelDirectory.json");

        ElementsBinder.bind(sld);

        /// конвертирование. Внутри converter есть сам modelBuilder
        SldToCimConverter converter = new SldToCimConverter();
        converter.convert(sld, dev, vol);

        ModelBuilder modelBuilder = converter.getModelBuilder();

        /// создаем файл сим модели в формате xml
        String cimModelXml = converter.getResult(".xml", RDFFormat.RDFXML);



        /* ЛР2 */



        File initialFile = new File("src/test/resources/LR1_cimModel2.xml");
        InputStream targetStream = new FileInputStream(initialFile);

//        RDFParser parser = createParser(RDFFormat.RDFXML);
//        parser.setParserConfig()

        Model model = Rio.parse(targetStream,"http://iec.ch/TC57/2013/CIM-schema-cim16#", RDFFormat.RDFXML);

        CimToJavaConverter cimToJavaConverter = new CimToJavaConverter();
        cimToJavaConverter.converterCimToJava(model);

//        SparQL spar = new SparQL();
//        spar.RDFtoJavaClass(model);


        System.out.println(cimModelXml);
    }
}


//<dependency>
//<groupId>org.slf4j</groupId>
//<artifactId>slf4j-simple</artifactId>
//<version>1.7.21</version>
//</dependency>
