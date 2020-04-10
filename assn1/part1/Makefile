clean:
	- rm *.png

build:
	javac Main.java

snowman: build
	java Main -image snowman.png -size 1000 -threshold 5000 -xlo -1.236 -xhi -1.191 -ylo 0.14 -yhi 0.172

fields: build
	java Main -image fields.png -size 1000 -threshold 5000 -xlo -0.74998880248225142145 -xhi -0.74998880228812666519 -ylo 0.00699725115971273323 -yhi 0.00699725130530630042

dragon: build
	java Main -image dragon.png -size 4000 -threshold 250 -xlo -0.840716 -xhi -0.840732 -ylo 0.22420 -yhi 0.224216

special: build
	java Main -image special.png -size 1000 -threshold 450 -xlo -0.771536 -xhi -0.771537 -ylo -0.115171 -yhi -0.115172

all: snowman fields dragon special
