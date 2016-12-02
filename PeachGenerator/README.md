# A tool for healthcare data research and healthcare system development.

The main use cases for this tool are in healthcare data, where we often need sample patient data for research or systems development. 
Patient data is difficult to work with, due to the legal constraints within which we must work. Therefore this tool will be useful for the following problems:

## Anonymising datasets for research
Such that the information cannot be tracked back to the patients.
This removes patient identifiers and may alter some fields, but the meaning within the remainder of the data is maintained, allowing it to be analysed to look for trends and correlations. 

## Creating novel datasets based on existing data structures and sample data.
In this case all the data is "fake", in that it does not actually belong to real patients. However the data should "look similar" to real data.
We envision that Generator is deployed within a healthcare institution and analyses real data, before making novel data sets which follow similar distributions to the real data (by examining the schema and real patient data).
This would not be useful for research, but is useful for generating sample datasets (which can be used outside the hospital) to help build and test new systems.

 
