years = ["year1.txt", "year2.txt", "year3.txt", "year4.txt"]
arrs = []
for i in years:
    with open("years/"+i, "r") as f:
        arrs.append([i for i in f])
