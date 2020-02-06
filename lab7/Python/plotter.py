import matplotlib.pyplot as plt
import os


def plot_java():
    current_directory = os.path.dirname(os.getcwd())
    java_directory = os.path.join(current_directory, "Java")

    naive_data = dict()
    greedy_data = dict()
    asynchronous_data = dict()
    waiter_data = dict()
    with open(os.path.join(java_directory, "naivePhilosophers.txt")) as file:
        naive_5 = []
        naive_10 = []
        naive_20 = []
        while True:
            line = file.readline().strip()
            if line == "":
                break
            else:
                naive_5.append(int(line))

        while True:
            line = file.readline().strip()
            if line == "":
                break
            else:
                naive_10.append(int(line))

        while True:
            line = file.readline().strip()
            if line == "":
                break
            else:
                naive_20.append(int(line))

        naive_data[5] = naive_5
        naive_data[10] = naive_10
        naive_data[20] = naive_20

    with open(os.path.join(java_directory, "greedyPhilosophers.txt")) as file:
        greedy_5 = []
        greedy_10 = []
        greedy_20 = []
        while True:
            line = file.readline().strip()
            if line == "":
                break
            else:
                greedy_5.append(int(line))

        while True:
            line = file.readline().strip()
            if line == "":
                break
            else:
                greedy_10.append(int(line))

        while True:
            line = file.readline().strip()
            if line == "":
                break
            else:
                greedy_20.append(int(line))

        greedy_data[5] = greedy_5
        greedy_data[10] = greedy_10
        greedy_data[20] = greedy_20

    with open(os.path.join(java_directory, "asynchronousPhilosophers.txt")) as file:
        asynchronous_5 = []
        asynchronous_10 = []
        asynchronous_20 = []
        while True:
            line = file.readline().strip()
            if line == "":
                break
            else:
                asynchronous_5.append(int(line))

        while True:
            line = file.readline().strip()
            if line == "":
                break
            else:
                asynchronous_10.append(int(line))

        while True:
            line = file.readline().strip()
            if line == "":
                break
            else:
                asynchronous_20.append(int(line))

        asynchronous_data[5] = asynchronous_5
        asynchronous_data[10] = asynchronous_10
        asynchronous_data[20] = asynchronous_20

    with open(os.path.join(java_directory, "waiterPhilosophers.txt")) as file:
        waiter_5 = []
        waiter_10 = []
        waiter_20 = []
        while True:
            line = file.readline().strip()
            if line == "":
                break
            else:
                waiter_5.append(int(line))

        while True:
            line = file.readline().strip()
            if line == "":
                break
            else:
                waiter_10.append(int(line))

        while True:
            line = file.readline().strip()
            if line == "":
                break
            else:
                waiter_20.append(int(line))

        waiter_data[5] = waiter_5
        waiter_data[10] = waiter_10
        waiter_data[20] = waiter_20

    # 5 philosophers
    plt.plot(naive_data[5], label="naive")
    plt.plot(greedy_data[5], label="greedy")
    plt.plot(asynchronous_data[5], label="asynchronous")
    plt.legend(loc="upper right")
    plt.savefig('java_5_no_waiter.png')
    plt.clf()

    plt.plot(naive_data[5], label="naive")
    plt.plot(greedy_data[5], label="greedy")
    plt.plot(asynchronous_data[5], label="asynchronous")
    plt.plot(waiter_data[5], label="waiter")
    plt.legend(loc="upper right")
    plt.savefig('java_5_waiter.png')
    plt.clf()

    # 10 philosophers
    plt.plot(naive_data[10], label="naive")
    plt.plot(greedy_data[10], label="greedy")
    plt.plot(asynchronous_data[10], label="asynchronous")
    plt.legend(loc="upper right")
    plt.savefig('java_10_no_waiter.png')
    plt.clf()

    plt.plot(naive_data[10], label="naive")
    plt.plot(greedy_data[10], label="greedy")
    plt.plot(asynchronous_data[10], label="asynchronous")
    plt.plot(waiter_data[10], label="waiter")
    plt.legend(loc="upper right")
    plt.savefig('java_10_waiter.png')
    plt.clf()

    # 20 philosophers
    plt.plot(naive_data[20], label="naive")
    plt.plot(greedy_data[20], label="greedy")
    plt.plot(asynchronous_data[20], label="asynchronous")
    plt.legend(loc="upper right")
    plt.savefig('java_20_no_waiter.png')
    plt.clf()

    plt.plot(naive_data[20], label="naive")
    plt.plot(greedy_data[20], label="greedy")
    plt.plot(asynchronous_data[20], label="asynchronous")
    plt.plot(waiter_data[20], label="waiter")
    plt.legend(loc="upper right")
    plt.savefig('java_20_waiter.png')
    plt.clf()


def plot_python():
    naive_data = dict()
    asynchronous_data = dict()
    with open("naivePhilosophers.txt") as file:
        naive_5 = []
        naive_10 = []
        naive_20 = []
        while True:
            line = file.readline().strip()
            if line == "":
                break
            else:
                naive_5.append(int(line))

        while True:
            line = file.readline().strip()
            if line == "":
                break
            else:
                naive_10.append(int(line))

        while True:
            line = file.readline().strip()
            if line == "":
                break
            else:
                naive_20.append(int(line))

    naive_data[5] = naive_5
    naive_data[10] = naive_10
    naive_data[20] = naive_20

    with open("asynchronousPhilosophers.txt") as file:
        asynchronous_5 = []
        asynchronous_10 = []
        asynchronous_20 = []
        while True:
            line = file.readline().strip()
            if line == "":
                break
            else:
                asynchronous_5.append(int(line))

        while True:
            line = file.readline().strip()
            if line == "":
                break
            else:
                asynchronous_10.append(int(line))

        while True:
            line = file.readline().strip()
            if line == "":
                break
            else:
                asynchronous_20.append(int(line))

    asynchronous_data[5] = asynchronous_5
    asynchronous_data[10] = asynchronous_10
    asynchronous_data[20] = asynchronous_20

    plt.plot(naive_data[5], label="naive")
    plt.plot(asynchronous_data[5], label="asynchronous")
    plt.legend(loc="upper right")
    plt.savefig('python_5.png')
    plt.clf()

    plt.plot(naive_data[10], label="naive")
    plt.plot(asynchronous_data[10], label="asynchronous")
    plt.legend(loc="upper right")
    plt.savefig('python_10.png')
    plt.clf()

    plt.plot(naive_data[20], label="naive")
    plt.plot(asynchronous_data[20], label="asynchronous")
    plt.legend(loc="upper right")
    plt.savefig('python_20.png')
    plt.clf()


if __name__ == "__main__":
    #plot_java()
    #plot_python()
