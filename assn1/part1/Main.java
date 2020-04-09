import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Main
{
    public static void main(String[] args)
    {
	String imageName = "default.png";
	int size = 100;
	int threshold = 0;
	double xlo = 0.0;
	double xhi = 0.0;
	double ylo = 0.0;
	double yhi = 0.0;
	
	for(int i = 0; i < args.length - 1; i++)
	{
	    String argName = args[i];
	    String argValue = args[i + 1];
	    switch(argName.toLowerCase())
	    {
	    case "-image":
		imageName = argValue;
		break;
	    case "-size":
		size = Integer.parseInt(argValue);
		break;
	    case "-threshold":
		threshold = Integer.parseInt(argValue);
		break;
	    case "-xlo":
		xlo = Double.parseDouble(argValue);
		break;
	    case "-xhi":
		xhi = Double.parseDouble(argValue);
		break;
	    case "-ylo":
		ylo = Double.parseDouble(argValue);
		break;
	    case "-yhi":
		yhi = Double.parseDouble(argValue);
		break;
	    }
	}

	BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
	Color[] pixels = new Color[size * size];
	for(int i = 0; i < size * size; i++)
	{
	    int x = i % size;
	    int y = i / size;
	    double xc = xlo + (xhi - xlo) * x / size;
	    double yc = xlo + (xhi - xlo) * x / size;

	    int iters = compute(xc, yc, threshold);

	    if(iters < threshold)
		image.setRGB(x, y, Color.BLACK.getRGB());
	    else
		image.setRGB(x, y, Color.WHITE.getRGB());
	}

	try
	{
	    ImageIO.write(image, "png", new File(imageName));
	}
	catch(IOException e)
	{
	    e.printStackTrace();
	}
    }

    public static int compute(double xc, double yc, int threshold)
    {
	int i = 0;
	double x = 0.0;
	double y = 0.0;
	while(x * x + y * y < 2 && i < threshold)
	{
	    double xt = x * x - y * y + xc;
	    double yt = 2 * x * y + yc;
	    x = xt;
	    y = yt;
	    i += 1;
	}
	return i;
    }
}




