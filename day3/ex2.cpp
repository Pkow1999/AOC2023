#include <fstream>
#include <iostream>
#include <algorithm>
#include <map>
int main()
{

    const std::string pathToInput = "Input";
    
    std::ifstream file(pathToInput);
    std::string line;

    std::vector< std::string > stringBuffer = std::vector< std::string > ();//vector with all the lines
    std::vector< std::tuple<int, int> > vectorOfPosNumberToCheck = std::vector< std::tuple<int, int> >();//vector with begin and end indexes with numbers to check
    std::map<std::tuple<int, int>, std::vector<int> > mapWithGearToNumber = std::map<std::tuple<int, int>, std::vector<int>>();//a map with pos of gear and all adjective numbers
    
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
            int offsetBegLength;
            int offsetEndLength;

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
                        if(stringBuffer.at(i)[j] == '*' )//we got the gear, now lets save position of this gear and number adjective to it
                        {
                            if(mapWithGearToNumber.find(std::make_tuple(i, j)) == mapWithGearToNumber.end())
                            {
                                std::vector<int> value = std::vector<int>();
                                value.push_back(std::stoi( line.substr(begOfNumber, endOfNumber - begOfNumber + 1) ) );
                                mapWithGearToNumber[std::make_tuple(i,j)] = value;
                            }
                            else
                            {
                                mapWithGearToNumber.at(std::make_tuple(i, j)).push_back(std::stoi( line.substr(begOfNumber, endOfNumber - begOfNumber + 1) ));
                            }
                            break;
                        }
                    }
                }
                }
        }
    }
    int sum = 0;
    for (auto [key, value] : mapWithGearToNumber)
    {
        std::cout << '[' << std::get<0>(key) << " " <<std::get<1>(key)  << "] = " ;
        for(auto& num : value)
        {
            std::cout << num << ", ";
        }
        if(value.size() == 2)//only use this gear that have only 2 adjective numbers
        {
            sum += value.front() * value.back();
        }
        std::cout<<"\n";
    }
    std::cout<<"SUM: " << sum << "\n";
}