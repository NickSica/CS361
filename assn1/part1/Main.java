import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Main
{
    public static void main(String[] args)
    {
	final double ONE_THIRD = 1.0 / 3.0;
	final double TWO_THIRDS = 2 * ONE_THIRD;
	final double MIN_COLOR_VAL = 0.1;
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

	long startTime = System.currentTimeMillis();
	for(int i = 0; i < size * size; i++)
	{
	    int x = i % size;
	    int y = i / size;
	    double xc = xlo + (xhi - xlo) * x / size;
	    double yc = ylo + (yhi - ylo) * y / size;

	    int iters = compute(xc, yc, threshold);
	    double segment = (double) iters / (double) threshold;
	    float r = 0.0f;
	    float g = 0.0f;
	    float b = 0.0f;
	    if(segment <= ONE_THIRD)
	    {
		r = (float)Math.max(MIN_COLOR_VAL, segment * 3.0);
		g = (float)Math.max(MIN_COLOR_VAL, segment * 3.0);
	    }

	    if(segment > ONE_THIRD && segment <= TWO_THIRDS)
		g = (float)Math.max(MIN_COLOR_VAL, (segment - ONE_THIRD) * 3.0);
	    
	    if(segment >= TWO_THIRDS)
	    {
		r = (float)Math.max(MIN_COLOR_VAL, (segment - TWO_THIRDS) * 3.0);
		b = (float)Math.max(MIN_COLOR_VAL, (segment - TWO_THIRDS) * 3.0);
	    }

	    Color color = new Color(r, g, b);	    
	    image.setRGB(x, y, color.getRGB());
	}
	long endTime = System.currentTimeMillis();
	float totalTime = (endTime - startTime) / 1000.0f;
	System.out.printf("Total time: %.2f\n", totalTime);

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




