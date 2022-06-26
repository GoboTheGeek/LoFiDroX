console.log('starting gtg-utils-tooling.js');

class GtgUtilsTools {
    // return true is value is null or undefined
    static isNull(value) {
        return (('undefined' == typeof value) || (null === value));
    };

    static isNullOrEmpty(value) {
        return (GtgUtilsTools.isNull(value) || (0 === value.trim().length));
    };

    // return trus if value is a function
    static isFunc(value) {
        return (!GtgUtilsTools.isNull(value) && ('function' == typeof value));
    };

    // return true is value is an array
    static isArray(value) {
        return (!GtgUtilsTools.isNull(value) && Array.isArray(value));
    };

    // return true if value is a non null string
    static checkString(value) {
        return !GtgUtilsTools.isNull(value) && (typeof value === 'string' || value instanceof String) && (0 < value.trim().length);
    };

    // extract named parameter form headers iterator
    static extractHeaderValue(headers, param) {
        if (!GtgUtilsTools.isNull(headers) && !GtgUtilsTools.isNull(headers.entries()) && !GtgUtilsTools.isNull(param)) {
            for (let head of headers.entries()) {
                if (head[0].toLowerCase() === param.toLowerCase()) { return head[1]; }
            }
            console.error('There is no [' + param + '] in headers');
            return null;
        }
        console.error('Can\'t extract due to missing headers or parameter name');
    };

    // set value to local storage
    static setLocalValue(key, value) {
        window.localStorage.setItem(key, value);
    };

    // get value from local storage
    static getLocalValue(key) {
        return window.localStorage.getItem(key);
    };

    // return an array containing { fieldname: error message }
    static turnYupErrorsToArray(errors) {
        return errors.inner.reduce((field, error) => {
            return { ...field, [error.path]: error.message };
        }, {});
    };
};

if (typeof JSON.decycle !== "function") {
    JSON.decycle = function decycle(object, replacer) {
        "use strict";
        var objects = new WeakMap();     // object to path mappings

        return (function derez(value, path) {
            var old_path;   // The path of an earlier occurance of value
            var nu;         // The new object or array
            if (replacer !== undefined) {
                value = replacer(value);
            }
            if (
                typeof value === "object"
                && value !== null
                && !(value instanceof Boolean)
                && !(value instanceof Date)
                && !(value instanceof Number)
                && !(value instanceof RegExp)
                && !(value instanceof String)
            ) {
                old_path = objects.get(value);
                if (old_path !== undefined) {
                    return {$ref: old_path};
                }
                objects.set(value, path);
                if (Array.isArray(value)) {
                    nu = [];
                    value.forEach(function (element, i) {
                        nu[i] = derez(element, path + "[" + i + "]");
                    });
                } else {
                    nu = {};
                    Object.keys(value).forEach(function (name) {
                        nu[name] = derez(
                            value[name],
                            path + "[" + JSON.stringify(name) + "]"
                        );
                    });
                }
                return nu;
            }
            return value;
        }(object, "$"));
    };
}

if (typeof JSON.retrocycle !== "function") {
    JSON.retrocycle = function retrocycle($) {
        "use strict";
        var px = /^\$(?:\[(?:\d+|"(?:[^\\"\u0000-\u001f]|\\(?:[\\"\/bfnrt]|u[0-9a-zA-Z]{4}))*")\])*$/;

        (function rez(value) {
            if (value && typeof value === "object") {
                if (Array.isArray(value)) {
                    value.forEach(function (element, i) {
                        if (typeof element === "object" && element !== null) {
                            var path = element.$ref;
                            if (typeof path === "string" && px.test(path)) {
                                value[i] = eval(path);
                            } else {
                                rez(element);
                            }
                        }
                    });
                } else {
                    Object.keys(value).forEach(function (name) {
                        var item = value[name];
                        if (typeof item === "object" && item !== null) {
                            var path = item.$ref;
                            if (typeof path === "string" && px.test(path)) {
                                value[name] = eval(path);
                            } else {
                                rez(item);
                            }
                        }
                    });
                }
            }
        }($));
        return $;
    };
};

export default GtgUtilsTools;

console.log('done gtg-utils-tooling.js');
