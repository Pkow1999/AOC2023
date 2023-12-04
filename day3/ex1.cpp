#include <fstream>
#include <iostream>
#include <algorithm>

int main()
{
    const std::string pathToInput = "Input";
    
    std::ifstream file(pathToInput);
    std::string line;

    std::vector< std::string > stringBuffer = std::vector< std::string > ();//vector with all the lines
    std::vector<int> vectorOfNumbers = std::vector< int >();//vector with all numbers to sum
    std::vector< std::tuple<int, int> > vectorOfPosNumberToCheck = std::vector< std::tuple<int, int> >();//vector with begin and end indexes with numbers to check
    
    bool number = false;
    int begOfNumber = -1;
    int endOfNumber = -1;

    while(std::getline(file, line))
    {
        stringBuffer.push_back(line);
    }
    file.close();
    for(int lineNr = 0; lineNr < stringBuffer.size(); ++lineNr)//work on line to line basis
    {
        vectorOfPosNumberToCheck.clear();
        line = stringBuffer[lineNr];
        begOfNumber = -1;
        endOfNumber = -1;
        number = false;
        for(int charPos = 0; charPos < line.size(); ++charPos)//check all characters in line
        {
            if(line[charPos] >= 48 && line[charPos] <=57)
            {
                if(!number)
                {
                    number = true;
                    begOfNumber = charPos;//get the index where the number begins
                }
                if(charPos == line.size() - 1)//full number is in last characters in line
                {
                    endOfNumber = charPos;
                    vectorOfPosNumberToCheck.push_back(std::make_tuple(begOfNumber, endOfNumber));
                }
            
            }
            else
            {
                if(number)//found the full number: the next character is NAN
                {
                    endOfNumber = charPos - 1;
                    vectorOfPosNumberToCheck.push_back(std::make_tuple(begOfNumber, endOfNumber));
                    number = false;
                }
            }
        }
        if(!vectorOfPosNumberToCheck.empty())//found a number
        {
            int offsetBegLength = 0;
            int offsetEndLength = 0;

            int offsetBegHeight = 0;
            int offsetEndHeight = 0;
            
            //making sure i'm not out of bounds
            if(lineNr == 0)
                offsetBegHeight = 1;
            if(lineNr == stringBuffer.size() - 1)
                offsetEndHeight = 1;
            
            for(std::tuple posOfNumber : vectorOfPosNumberToCheck)
            {
                bool goodNumber = false;

                begOfNumber = std::get<0>(posOfNumber);
                endOfNumber = std::get<1>(posOfNumber);
                offsetBegLength = 0;
                offsetEndLength = 0;

                if(begOfNumber == 0)
                    offsetBegLength = 1;
                if(endOfNumber == line.size() - 1 )
                    offsetEndLength = 1;

                for(int i = lineNr - 1 + offsetBegHeight; i <= lineNr + 1 - offsetEndHeight; ++i)
                {
                    if(goodNumber)
                    {
                        break;
                    }
                    for(int j = begOfNumber - 1 + offsetBegLength; j <= endOfNumber + 1 - offsetEndLength; ++j)
                    {
                        if(stringBuffer.at(i)[j] != '.' )//if its not a dot and not a number then it must be a symbol
                        {
                            if(stringBuffer.at(i)[j] < 48 || stringBuffer.at(i)[j] > 57 )
                            {
                                goodNumber = true;
                                vectorOfNumbers.push_back( std::stoi(line.substr(begOfNumber, endOfNumber - begOfNumber + 1)) );   
                                break;
                            }
                        }
                    }
                }
                }
        }
    }
    int sum = 0;
    for(int num : vectorOfNumbers)
    {
        //std::cout<<num<<"\n";
        sum+=num;
    }
    std::cout<<"SUM: " << sum << "\n";
}