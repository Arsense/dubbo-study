package serializer;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author tangwei
 * @date 2018/7/12 19:38
 */
public class XmlSerializer implements  DataSerializer{

    private static final XStream xStream = new XStream(new DomDriver());

    public <T> byte[] serialize(T Object) {
        return xStream.toXML(Object).getBytes();
    }

    public <T> T unserialize(byte[] data, Class<T> clazz) throws ClassNotFoundException {
        String xml = new String(data);
        return (T) xStream.fromXML(xml);
    }

}
