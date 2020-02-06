from philosopher_asynchronous import PhilosopherAsynchronous
from philosopher_greedy import PhilosopherGreedy
from philosopher_naive import PhilosopherNaive
from philosopher_waiter import PhilosopherWaiter, Semaphore, Fork
from threading import Lock


def task1(N):
    avg_times = []
    for j in range(100):
        moving = [Lock() for _ in range(N)]
        taken = [False for _ in range(N)]
        forks = [object() for _ in range(N)]

        philosophers = [PhilosopherNaive(i, moving, taken, forks) for i in range(N)]

        for philosopher in philosophers:
            philosopher.start()

        for philosopher in philosophers:
            philosopher.join()

        times_sum = 0
        for philosopher in philosophers:
            times_sum += philosopher.time

        avg_times.append(times_sum // N)

    with open("naivePhilosophers.txt", "a") as file:
        for avg_time in avg_times:
            file.write(str(avg_time))
            file.write("\n")
        file.write("\n")


def task2(N):
    avg_times = []
    for j in range(100):
        locks = [Lock() for _ in range(N)]
        forks = [object() for _ in range(N)]

        philosophers = [PhilosopherGreedy(i, locks, forks) for i in range(N)]

        for philosopher in philosophers:
            philosopher.start()

        for philosopher in philosophers:
            philosopher.join()

        times_sum = 0
        for philosopher in philosophers:
            times_sum += philosopher.time

        avg_times.append(times_sum // N)

    with open("greedyPhilosophers.txt", "a") as file:
        for avg_time in avg_times:
            file.write(str(avg_time))
            file.write("\n")
        file.write("\n")


def task3(N):
    avg_times = []
    for j in range(100):
        moving = [Lock() for _ in range(N)]
        taken = [False for _ in range(N)]
        forks = [object() for _ in range(N)]

        philosophers = [PhilosopherAsynchronous(i, moving, taken, forks) for i in range(N)]

        for philosopher in philosophers:
            philosopher.start()

        for philosopher in philosophers:
            philosopher.join()

        times_sum = 0
        for philosopher in philosophers:
            times_sum += philosopher.time

        avg_times.append(times_sum // N)

    with open("asynchronousPhilosophers.txt", "a") as file:
        for avg_time in avg_times:
            file.write(str(avg_time))
            file.write("\n")
        file.write("\n")


def task4(N):
    avg_times = []
    for j in range(100):
        waiter = Semaphore(N - 1)
        forks = [Fork() for _ in range(N)]
        philosophers = [PhilosopherWaiter(i, forks, waiter) for i in range(N)]

        for philosopher in philosophers:
            philosopher.start()

        for philosopher in philosophers:
            philosopher.join()

        times_sum = 0
        for philosopher in philosophers:
            times_sum += philosopher.time

        avg_times.append(times_sum // N)

    with open("waiterPhilosophers.txt", "a") as file:
        for avg_time in avg_times:
            file.write(str(avg_time))
            file.write("\n")
        file.write("\n")


if __name__ == "__main__":
    for i in [5, 10, 20]:
        #task1(i)  # deadlock
        task2(i)
        #task3(i)  # deadlock
        task4(i)
