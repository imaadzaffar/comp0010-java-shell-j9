import csv

print("package | class | instructions | branches")

filename = 'target/site/jacoco/jacoco.csv'
with open(filename) as f:
    reader = csv.reader(f, delimiter=',')
    next(reader)

    missed_instructions = 0
    total_instructions = 0
    missed_branches = 0
    total_branches = 0

    for values in reader:
        val_package = values[1].split('.')[-1]
        val_class = values[2]
        val_instructions = str(100 - round(int(values[3]) / (int(values[3])+int(values[4])) * 100)) + "%" if int(values[4]) else 'n/a'
        val_branches = str(100 - round(int(values[5]) / (int(values[5])+int(values[6])) * 100)) + "%" if int(values[6]) else 'n/a'

        missed_instructions += int(values[3])
        total_instructions += int(values[3]) + int(values[4])
        missed_branches += int(values[5])
        total_branches += int(values[5]) + int(values[6])

        print(val_package, "|", val_class, "|", val_instructions, "|", val_branches)
    
    cov_instructions = 100 - round(missed_instructions/total_instructions*100)
    cov_branches = 100 - round(missed_branches/total_branches*100)
    print("\n-- Code Coverage --")
    print("Instructions:", str(cov_instructions) + "%")
    print("Branches:", str(cov_branches) + "%")
