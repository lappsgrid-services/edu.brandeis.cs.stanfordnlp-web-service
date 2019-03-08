package edu.brandeis.lapps.stanford.corenlp;

import org.junit.Test;
import org.lappsgrid.metadata.IOSpecification;
import org.lappsgrid.metadata.ServiceMetadata;
import org.lappsgrid.serialization.Data;
import org.lappsgrid.serialization.Serializer;
import org.lappsgrid.serialization.lif.Annotation;
import org.lappsgrid.serialization.lif.Container;
import org.lappsgrid.serialization.lif.View;

import java.util.List;

import static org.junit.Assert.*;
import static org.lappsgrid.discriminator.Discriminators.Uri;

/**
 * <i>TestPOSTagger.java</i> Language Application Grids (<b>LAPPS</b>)
 * <p> 
 * <p> Test cases are from <a href="http://www.programcreek.com/2012/05/opennlp-tutorial/">OpenNLP Tutorial</a>
 * <p> 
 *
 * @author Chunqi Shi ( <i>shicq@cs.brandeis.edu</i> )<br>Nov 20, 2013<br>
 *
 */
public class TestPOSTagger extends TestService {


    public TestPOSTagger() {
        service = new POSTagger();
        testText = "Good morning.";
    }

    @Test
    public void testMetadata(){
        ServiceMetadata metadata = super.testCommonMetadata();
        IOSpecification requires = metadata.getRequires();
        IOSpecification produces = metadata.getProduces();
        assertEquals("Expected 1 annotation, found: " + produces.getAnnotations().size(),
                1, produces.getAnnotations().size());
        assertTrue("POS tags not produced", produces.getAnnotations().contains(Uri.POS));
    }

    @Test
    public void testExecute(){

        Container executionResult = super.testExecuteFromPlainAndLIFWrapped();

        View view = executionResult.getView(0);
        List<Annotation> annotations = view.getAnnotations();

        assertEquals("Tokens", 3, annotations.size());
        Annotation annotation = annotations.get(0);
        assertEquals("@type is not correct: " + annotation.getAtType(), annotation.getAtType(), Uri.TOKEN);
        String goodPos = annotation.getFeature("pos");
        assertEquals("Correct tag for 'Good' is JJ. Found: " + goodPos, goodPos, "JJ");

    }
}