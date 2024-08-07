CC=gcc
CFLAGS=-Wno-pointer-to-int-cast -Wno-parentheses-equality -I ../../../chesslib/include
SOURCES=$(shell ls *.c)
OBJECTS=$(SOURCES:.c=.o) ../../../chesslib/bin/libchesslib.a
EXECUTABLE=../bin/cbot3020

all:  $(SOURCES) $(EXECUTABLE)

$(EXECUTABLE): $(OBJECTS)
	$(CC) $(CFLAGS) $^ -o $@

.o:
	$(CC) $(CFLAGS) -c $<

