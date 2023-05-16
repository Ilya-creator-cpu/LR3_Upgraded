package package1;

import lombok.Setter;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Resource {
    private List<Element> elements  = new ArrayList<>();
}
