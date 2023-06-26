const fs = require("fs")

function sum(obj, blacklistValue) {
    if (typeof obj === "number") {
        return obj
    }
    if (Array.isArray(obj)) {
        return obj.reduce((acc, v) => acc + sum(v, blacklistValue), 0)
    }
    if (typeof obj === "object") {
        const values = Object.values(obj)
        if (blacklistValue != null && values.includes(blacklistValue)) {
            return 0
        }
        return values.reduce((acc, v) => acc + sum(v, blacklistValue), 0)
    }
    return 0
}

function part1() {
    const input = readInput("Day12.txt")
    return sum(JSON.parse(input))
}

function part2() {
    const input = readInput("Day12.txt")
    return sum(JSON.parse(input), "red")
}

function readInput(fileName) {
    return fs.readFileSync(`./${fileName}`).toString()
}

const p1 = part1()
console.log(p1)
const p2 = part2()
console.log(p2);
