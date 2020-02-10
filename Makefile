
JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java


CLASSES = \
        CommandWords.java \
        Game.java \
        Parser.java \
        Test.java \
        Command.java \
        Room.java \
        Item.java \
        Player.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class *.ctxt