import matplotlib.pyplot as plt

traditional_readers = []
traditional_writers = []
paper_readers = []
paper_writers = []

with open("traditional_readers.txt") as file:
    for line in file:
        if line.strip() != "":
            traditional_readers.append(int(line.strip()))

with open("traditional_writers.txt") as file:
    for line in file:
        if line.strip() != "":
            traditional_writers.append(int(line.strip()))

with open("paper_readers.txt") as file:
    for line in file:
        if line.strip() != "":
            paper_readers.append(int(line.strip()))

with open("paper_writers.txt") as file:
    for line in file:
        if line.strip() != "":
            paper_writers.append(int(line.strip()))

plt.plot(traditional_readers, label="traditional_readers")
plt.plot(traditional_writers, label="traditional_writers")
plt.plot(paper_readers, label="paper_readers")
plt.plot(paper_writers, label="paper_writers")
plt.legend(loc="upper right")
plt.savefig('all.png')
plt.clf()

plt.plot(traditional_readers, label="traditional_readers")
plt.plot(traditional_writers, label="traditional_writers")
plt.legend(loc="upper right")
plt.savefig('traditional.png')
plt.clf()

plt.plot(paper_readers, label="paper_readers")
plt.plot(paper_writers, label="paper_writers")
plt.legend(loc="upper right")
plt.savefig('paper.png')
plt.clf()

plt.plot(traditional_readers, label="traditional_readers")
plt.plot(paper_readers, label="paper_readers")
plt.legend(loc="upper right")
plt.savefig('readers.png')
plt.clf()

plt.plot(traditional_writers, label="traditional_writers")
plt.plot(paper_writers, label="paper_writers")
plt.legend(loc="upper right")
plt.savefig('writers.png')
plt.clf()
