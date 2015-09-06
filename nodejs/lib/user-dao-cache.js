/**
 * memcached dao for User
 */ 

var Memcached = require( 'memcached' ), 
    target = require('./user-dao-sql'), 
    opt = require('../config/cache.js'), 
    conn;
    
var conn = null;

var UserDao = module.exports = {};

/**
 * get db connectioin
 */ 
var getConnection = function() {
    if(!conn) {
        conn = new Memcached(opt.hosts);
    }
    return conn;
};

/**
 * close db connection
 */ 
var closeConnection = function() {
    if(!conn) return;
    conn.end();
    conn = null;
};

/**
 * generate memcached key for id
 */ 
var genKey = function(id) {
    return 'NODEJS_TEST_DEMO_ID_' + id;
};

/**
 * get user by id
 * 
 * @param id: user id
 * @param cb: callback function with two params: function(err, res). if id not exist, res will be null.
 */ 
UserDao.getById = function(id, cb) {
    if(!id) {
        throw new Error('invalid argumenet id:' + id);
    }
    var conn = getConnection();
    
    conn.get(genKey(id), function(err, res) {
        if(!res) {
            //cache miss
            console.log('cache miss, use sql:' + id);
            target.getById(id, function(err, res) {
                if(!err) {
                    //update cache
                    conn.set(genKey(id), res, 10, function(err2, res2) {
                        doFinal(cb, err, res);
                    });
                } else {
                    doFinal(cb, err, res);
                }
            });
        } else {
            doFinal(cb, err, res);
        }
    });
};

/**
 * insert user
 * 
 * @param user: user info
 * @param cb: callback function with two params: function(err, res). 
 */
UserDao.insert = function(user, cb) {
    if(!user) {
        throw new Error('invalid argumenet user:' + user);
    }
    
    //just pass to db dao
   target.insert(user, function(err, res) {
        doFinal(cb, err, res);
    });
};

/**
 * update user
 * 
 * @param user: user info
 * @param cb: callback function with two params: function(err, res). 
 */
UserDao.update = function(user, cb) {
    if(!user) {
        throw new Error('invalid argumenet user:' + user);
    }
    
    target.update(user, function(err, res) {
        if(!!err) {
            doFinal(cb, err, res);
            return;
        }
        
        //update cache if it has bean cached
        var conn = getConnection();
        conn.get(genKey(user.id), function(err, res) {
            if(!!res) {
                //cache nerver expire
                conn.set(genKey(user.id), user, 10, function(err, res) {
                    doFinal(cb, err, res);
                });
                return;
            }
            
            doFinal(cb, err, res);
        });
        
    });
};

/**
 * delete user by id
 * 
 * @param id: user id
 * @param cb: callback function with two params: function(err, res). 
 */
UserDao.deleteById = function(id, cb) {
    if(!id) {
        throw new Error('invalid argumenet id:' + id);
    }
    
    target.deleteById(id, function(err, res) {
        if(!err) {
            //clear cache
            var conn = getConnection();
            conn.del(genKey(id), function(err2, res2) {
                doFinal(cb, err, res);
            });
        } else {
            doFinal(cb, err, res);
        }
    });
};

/**
 * do the final operation. 
 * close memcached connection, invoke callback if it exists.
 */ 
var doFinal = function(cb) {
    closeConnection();
    if(!!cb && typeof(cb) == 'function') {
        var args = Array.prototype.slice.call(arguments, 1);
        cb.apply(null, args);
    }
};

UserDao.close = function() {
    closeConnection();
    target.close();
};