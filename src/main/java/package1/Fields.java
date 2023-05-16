package package1;

import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.Setter;
import lombok.Getter;

import java.util.List;

@Getter @Setter
public class Fields extends Identifier {

    private String name;
    private String value;
    private String directoryId;

}