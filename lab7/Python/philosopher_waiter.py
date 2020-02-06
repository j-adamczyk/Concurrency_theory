from threading import Condition, Lock, Thread
from time import sleep, time_ns


class Semaphore:
    def __init__(self, max_philosophers):
        self.lock = Condition(Lock())
        self.philosophers = max_philosophers

    def go_eat(self):
        with self.lock:
            self.philosophers += 1
            self.lock.notify()

    def go_think(self):
        with self.lock:
            while self.philosophers == 0:
                self.lock.wait()
            self.philosophers -= 1


class Fork:
    def __init__(self):
        self.lock = Condition(Lock())
        self.taken = False

    def take(self):
        with self.lock:
            while self.taken:
                self.lock.wait()
            self.taken = True
            self.lock.notifyAll()

    def put(self):
        with self.lock:
            while not self.taken:
                self.lock.wait()
            self.taken = False
            self.lock.notifyAll()


class PhilosopherWaiter(Thread):
    def __init__(self, num, forks, waiter):
        Thread.__init__(self)
        self.num = num  # philosopher number
        self.forks = forks
        self.waiter = waiter
        self.time = 0

    @staticmethod
    def think():
        sleep(0.001)

    @staticmethod
    def eat():
        sleep(0.001)

    def run(self):
        left = self.num
        right = self.num + 1
        if right >= len(self.forks):
            right = 0

        for i in range(100):
            self.think()

            self.waiter.go_eat()  # start service by butler

            start_waiting = time_ns()
            self.forks[left].take()
            self.forks[right].take()
            stop_waiting = time_ns()

            self.time += (stop_waiting - start_waiting)

            self.eat()

            self.forks[right].put()
            self.forks[left].put()

            self.waiter.go_think()
