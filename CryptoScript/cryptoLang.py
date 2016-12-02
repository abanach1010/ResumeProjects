####################################
#  Script Name: cryptoLanguage.py  #
#  Team Members:                   #
#    Alex Banach                   #
#    Paloma Samaniego              #
####################################



LOOKUPTABLEPATH = ("lookuptable.csv")         # path for the look up table
INPUTFILENAME = ("myInputFile.txt")           # path for the input file
NEWSCRIPTNAME = "newCryptoScript"             # global variable for python program we created
NEWSCRIPT = ("{0}.py".format(NEWSCRIPTNAME))  # global variable to declare newCryptoScript as a '.py'

from os import path as _path  # importing _path

"""
Simple Instructions
    -use the lookuptable.csv to find each character you need
    -write each character with a space inbetween
        ex. 05 54 40        ==> a=5
            03 56 57 05 58  ==> print(a)
    -This code will create a new python file and execute it automatically.



    -whatever "key" is, add that to your inputFile.txt values.
        *** This is another layer of security ***
"""


class _file():                           # This class opens and processes myInputFile.txt

    def _openFile(self, file, readOrWrite='r'):

        assert type(file) is str, "File must be of type string. Current Type = {0}".format(type(file)) #internal self check
        assert type(readOrWrite) is str, "readOrWrite must be of type string"                          #internal self check

        try:
            myFile = open(file, readOrWrite, 16777216)
        except:                                 # IOError as (errno, strerror):
            print
            "Error: " + file + " is invalid or file cannot be open"
            if (myFile != None): myFile.close()
            myFile = None

        return myFile

    def processFile(self, file, readOrWrite='r'):

        if ((readOrWrite.upper() == 'R') and (not _path.exists(file))):
            raise Exception("File " + file + " doesn't exist.")

        return self._openFile(file, readOrWrite)

    def readLines(self, fileObj):

        inputFileObjLines = fileObj.readlines()
        fileObj.close()

        return inputFileObjLines

    def writeLines(self, fileObj, lines):

        fileObj.writelines(lines)
        fileObj.close()


def csvParser(value):       # parser that looks up values in the table
    myFile = _file()
    csvFileObj = myFile.processFile(LOOKUPTABLEPATH, 'r')
    csvLines = myFile.readLines(csvFileObj)

    column = value % 10
    row = int(value / 10)

    csvLine = csvLines[row]
    retVal = csvLine.split(",")[column]

    return retVal


def parser(line, key):      # This parser adds lines to the newCryptoScript file and
                            # checks if the lines and values are of the correct type.

    assert type(line) is str, "line must be of type string"    # Checks for type string
    newLine = ""

    values = line.split()

    for value in values:               # Exception handling for type Int
        try:
            value = int(value)
        except:
            raise Exception("All values must be Integers")
        tempValue = (value - key)      # tempValue checks the key inserted in main() and
        if (tempValue < 0):
            tempValue = 89 - tempValue

        subLine = csvParser(tempValue)
        newLine = newLine + subLine

    return newLine


def main(key=0):                                            # Main function to execute code
    myFile = _file()
    inputFileObj = myFile.processFile(INPUTFILENAME, 'r')
    readLines = myFile.readLines(inputFileObj)
    newLines = []
    newLines.append("def main():\n")                        # begins each .py file with "def main():"
    for line in readLines:
        newLines.append("\t{0}\n".format(parser(line, key)))

    outputFileObj = myFile.processFile(NEWSCRIPT, 'w')      # starts new file object
    myFile.writeLines(outputFileObj, newLines)              # writes all new lines

    import newCryptoScript                                  # imports global class newCryptoScript
    newCryptoScript.main()                                  # executes our newly created python file



main()                                                      # Here you can add additional key values in main()
                                                            # ex. main(1) requires you to add 1 to each of your
                                                            # values found in myInputFile.txt
                                                            # can handle many other math operations, you would
                                                            # need to add whatever math operator to your values
                                                            # from the table.