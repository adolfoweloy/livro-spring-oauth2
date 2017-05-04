import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class TesteJwt {

	public static void main(String[] args) {

		String header = new String(
			Base64.getEncoder().encode(getHeaderInBytes()));

		String payload = new String(
			Base64.getEncoder().encode(getPayloadInBytes()));

		String token =  header + "." + payload + ".";

		System.out.println(token);
	}

	private static byte[] getHeaderInBytes() {
		return getFileContentInBytes("header.json");
	}

	private static byte[] getPayloadInBytes() {
		return getFileContentInBytes("payload.json");
	}

	private static byte[] getFileContentInBytes(String fileName) {
		URL resource = TesteJwt.class.getResource(fileName);

		try {
			byte[] byteContent = Files.readAllBytes(
				Paths.get(resource.toURI()));
			return byteContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
