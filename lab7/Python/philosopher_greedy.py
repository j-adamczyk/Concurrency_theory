import threading
from time import sleep, time_ns


class PhilosopherGreedy(threading.Thread):
    def __init__(self, num, locks, forks):
        threading.Thread.__init__(self)
        self.num = num
        self.locks = locks
        self.forks = forks
        self.left_fork = None
        self.right_fork = None
        self.time = 0

    @staticmethod
    def think():
        sleep(0.001)

    @staticmethod
    def eat():
        sleep(0.001)

    def run(self):
        i = 0
        while i < 100:
            self.think()

            left = self.num
            right = self.num + 1
            if right >= len(self.locks):
                right = 0

            start_waiting = time_ns()
            if self.locks[left].acquire(False):
                if self.locks[right].acquire(False):
                    stop_waiting = time_ns()
                    self.time += (stop_waiting - start_waiting)

                    self.left_fork = self.forks[left]
                    self.right_fork = self.forks[right]

                    self.eat()
                    i += 1

                    self.locks[right].release()
                    self.locks[left].release()
