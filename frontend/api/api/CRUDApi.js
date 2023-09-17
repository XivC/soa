/*
 * Dybov's and Lavrov's SoA lab-1
 * Some dragons service
 *
 * OpenAPI spec version: 1.0.0
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 *
 * Swagger Codegen version: 3.0.46
 *
 * Do not edit the class manually.
 *
 */
import {ApiClient} from "../ApiClient.js";
import {Color} from '../model/Color.js';
import {Dragon} from '../model/Dragon.js';
import {DragonType} from '../model/DragonType.js';
import {Filter} from '../model/Filter.js';
import {Person} from '../model/Person.js';
import {Sort} from '../model/Sort.js';

/**
* CRUD service.
* @module api/CRUDApi
* @version 1.0.0
*/
export class CRUDApi {

    /**
    * Constructs a new CRUDApi. 
    * @alias module:api/CRUDApi
    * @class
    * @param {module:ApiClient} [apiClient] Optional API client implementation to use,
    * default to {@link module:ApiClient#instanc
    e} if unspecified.
    */
    constructor(apiClient) {
        this.apiClient = apiClient || ApiClient.instance;
    }

    /**
     * Callback function to receive the result of the dragonsCountByTypeGet operation.
     * @callback moduleapi/CRUDApi~dragonsCountByTypeGetCallback
     * @param {String} error Error message, if any.
     * @param {'Number'{ data The data returned by the service call.
     * @param {String} response The complete HTTP response.
     */

    /**
     * Count dragons by type
     * @param {DragonType|String} type
     * @param {module:api/CRUDApi~dragonsCountByTypeGetCallback} callback The callback function, accepting three arguments: error, data, response
     * data is of type: {@link <&vendorExtensions.x-jsdoc-type>}
     */
    dragonsCountByTypeGet(type, callback) {
      
      let postBody = null;
      // verify the required parameter 'type' is set
      if (type === undefined || type === null) {
        throw new Error("Missing the required parameter 'type' when calling dragonsCountByTypeGet");
      }

      let pathParams = {
        
      };
      let queryParams = {
        'type': type
      };
      let headerParams = {
        
      };
      let formParams = {
        
      };

      let authNames = [];
      let contentTypes = [];
      let accepts = ['application/xml'];
      let returnType = 'Number.js';

      return this.apiClient.callApi(
        '/dragons/count-by-type', 'GET',
        pathParams, queryParams, headerParams, formParams, postBody,
        authNames, contentTypes, accepts, returnType, callback
      );
    }
    /**
     * Callback function to receive the result of the dragonsDragonIdDelete operation.
     * @callback moduleapi/CRUDApi~dragonsDragonIdDeleteCallback
     * @param {String} error Error message, if any.
     * @param data This operation does not return a value.
     * @param {String} response The complete HTTP response.
     */

    /**
     * Delete a dragon by ID
     * @param {Number} dragonId 
     * @param {module:api/CRUDApi~dragonsDragonIdDeleteCallback} callback The callback function, accepting three arguments: error, data, response
     */
    dragonsDragonIdDelete(dragonId, callback) {
      
      let postBody = null;
      // verify the required parameter 'dragonId' is set
      if (dragonId === undefined || dragonId === null) {
        throw new Error("Missing the required parameter 'dragonId' when calling dragonsDragonIdDelete");
      }

      let pathParams = {
        'dragonId': dragonId
      };
      let queryParams = {
        
      };
      let headerParams = {
        
      };
      let formParams = {
        
      };

      let authNames = [];
      let contentTypes = [];
      let accepts = [];
      let returnType = null;

      return this.apiClient.callApi(
        '/dragons/{dragonId}', 'DELETE',
        pathParams, queryParams, headerParams, formParams, postBody,
        authNames, contentTypes, accepts, returnType, callback
      );
    }
    /**
     * Callback function to receive the result of the dragonsDragonIdGet operation.
     * @callback moduleapi/CRUDApi~dragonsDragonIdGetCallback
     * @param {String} error Error message, if any.
     * @param {String} error Error message, if any.
     * @param {module:model/Dragon{ data The data returned by the service call.
     * @param {String} response The complete HTTP response.
     */

    /**
     * Get dragon by ID
     * @param {Number} dragonId 
     * @param {module:api/CRUDApi~dragonsDragonIdGetCallback} callback The callback function, accepting three arguments: error, data, response
     * data is of type: {@link <&vendorExtensions.x-jsdoc-type>}
     */
    dragonsDragonIdGet(dragonId, callback) {
      
      let postBody = null;
      // verify the required parameter 'dragonId' is set
      if (dragonId === undefined || dragonId === null) {
        throw new Error("Missing the required parameter 'dragonId' when calling dragonsDragonIdGet");
      }

      let pathParams = {
        'dragonId': dragonId
      };
      let queryParams = {
        
      };
      let headerParams = {
        
      };
      let formParams = {
        
      };

      let authNames = [];
      let contentTypes = [];
      let accepts = ['application/xml'];
      let returnType = Dragon;

      return this.apiClient.callApi(
        '/dragons/{dragonId}', 'GET',
        pathParams, queryParams, headerParams, formParams, postBody,
        authNames, contentTypes, accepts, returnType, callback
      );
    }
    /**
     * Callback function to receive the result of the dragonsDragonIdPut operation.
     * @callback moduleapi/CRUDApi~dragonsDragonIdPutCallback
     * @param {String} error Error message, if any.
     * @param data This operation does not return a value.
     * @param {String} response The complete HTTP response.
     */

    /**
     * Update a dragon by ID
     * @param {Number} dragonId 
     * @param {module:model/Dragon} dragon 
     * @param {module:api/CRUDApi~dragonsDragonIdPutCallback} callback The callback function, accepting three arguments: error, data, response
     */
    dragonsDragonIdPut(dragonId, dragon, callback) {
      
      let postBody = null;
      // verify the required parameter 'dragonId' is set
      if (dragonId === undefined || dragonId === null) {
        throw new Error("Missing the required parameter 'dragonId' when calling dragonsDragonIdPut");
      }
      // verify the required parameter 'dragon' is set
      if (dragon === undefined || dragon === null) {
        throw new Error("Missing the required parameter 'dragon' when calling dragonsDragonIdPut");
      }

      let pathParams = {
        'dragonId': dragonId
      };
      let queryParams = {
        'dragon': dragon
      };
      let headerParams = {
        
      };
      let formParams = {
        
      };

      let authNames = [];
      let contentTypes = [];
      let accepts = ['application/xml'];
      let returnType = null;

      return this.apiClient.callApi(
        '/dragons/{dragonId}', 'PUT',
        pathParams, queryParams, headerParams, formParams, postBody,
        authNames, contentTypes, accepts, returnType, callback
      );
    }
    /**
     * Callback function to receive the result of the dragonsFilterByColorGet operation.
     * @callback moduleapi/CRUDApi~dragonsFilterByColorGetCallback
     * @param {String} error Error message, if any.
     * @param {Array.<module:model/Dragon>{ data The data returned by the service call.
     * @param {String} response The complete HTTP response.
     */

    /**
     * Filter dragons by color
     * @param {module:model/Color} color 
     * @param {module:api/CRUDApi~dragonsFilterByColorGetCallback} callback The callback function, accepting three arguments: error, data, response
     * data is of type: {@link <&vendorExtensions.x-jsdoc-type>}
     */
    dragonsFilterByColorGet(color, callback) {
      
      let postBody = null;
      // verify the required parameter 'color' is set
      if (color === undefined || color === null) {
        throw new Error("Missing the required parameter 'color' when calling dragonsFilterByColorGet");
      }

      let pathParams = {
        
      };
      let queryParams = {
        'color': color
      };
      let headerParams = {
        
      };
      let formParams = {
        
      };

      let authNames = [];
      let contentTypes = [];
      let accepts = ['application/xml'];
      let returnType = [Dragon];

      return this.apiClient.callApi(
        '/dragons/filter-by-color', 'GET',
        pathParams, queryParams, headerParams, formParams, postBody,
        authNames, contentTypes, accepts, returnType, callback
      );
    }
    /**
     * Callback function to receive the result of the dragonsGet operation.
     * @callback moduleapi/CRUDApi~dragonsGetCallback
     * @param {String} error Error message, if any.
     * @param {Array.<module:model/Dragon>{ data The data returned by the service call.
     * @param {String} response The complete HTTP response.
     */

    /**
     * Get a list of dragons
     * @param {Object} opts Optional parameters
     * @param {Array.<module:model/Filter>} opts.filter 
     * @param {Array.<module:model/Sort>} opts.sort 
     * @param {module:api/CRUDApi~dragonsGetCallback} callback The callback function, accepting three arguments: error, data, response
     * data is of type: {@link <&vendorExtensions.x-jsdoc-type>}
     */
    dragonsGet(opts, callback) {
      opts = opts || {};
      let postBody = null;

      let pathParams = {
        
      };
      let queryParams = {
        'filter': this.apiClient.buildCollectionParam(opts['filter'], 'multi'),'sort': this.apiClient.buildCollectionParam(opts['sort'], 'multi')
      };
      let headerParams = {
        
      };
      let formParams = {
        
      };

      let authNames = [];
      let contentTypes = [];
      let accepts = ['application/xml'];
      let returnType = [Dragon];

      return this.apiClient.callApi(
        '/dragons', 'GET',
        pathParams, queryParams, headerParams, formParams, postBody,
        authNames, contentTypes, accepts, returnType, callback
      );
    }
    /**
     * Callback function to receive the result of the dragonsPost operation.
     * @callback moduleapi/CRUDApi~dragonsPostCallback
     * @param {String} error Error message, if any.
     * @param {module:model/Dragon{ data The data returned by the service call.
     * @param {String} response The complete HTTP response.
     */

    /**
     * Create a new dragon
     * @param {module:model/Dragon} dragon 
     * @param {module:api/CRUDApi~dragonsPostCallback} callback The callback function, accepting three arguments: error, data, response
     * data is of type: {@link <&vendorExtensions.x-jsdoc-type>}
     */
    dragonsPost(dragon, callback) {
      
      let postBody = null;
      // verify the required parameter 'dragon' is set
      if (dragon === undefined || dragon === null) {
        throw new Error("Missing the required parameter 'dragon' when calling dragonsPost");
      }

      let pathParams = {
        
      };
      let queryParams = {
        'dragon': dragon
      };
      let headerParams = {
        
      };
      let formParams = {
        
      };

      let authNames = [];
      let contentTypes = [];
      let accepts = ['application/xml'];
      let returnType = Dragon;

      return this.apiClient.callApi(
        '/dragons', 'POST',
        pathParams, queryParams, headerParams, formParams, postBody,
        authNames, contentTypes, accepts, returnType, callback
      );
    }
    /**
     * Callback function to receive the result of the dragonsSumAgeGet operation.
     * @callback moduleapi/CRUDApi~dragonsSumAgeGetCallback
     * @param {String} error Error message, if any.
     * @param {'Number'{ data The data returned by the service call.
     * @param {String} response The complete HTTP response.
     */

    /**
     * Calculate the sum of age for all dragons
     * @param {module:api/CRUDApi~dragonsSumAgeGetCallback} callback The callback function, accepting three arguments: error, data, response
     * data is of type: {@link <&vendorExtensions.x-jsdoc-type>}
     */
    dragonsSumAgeGet(callback) {
      
      let postBody = null;

      let pathParams = {
        
      };
      let queryParams = {
        
      };
      let headerParams = {
        
      };
      let formParams = {
        
      };

      let authNames = [];
      let contentTypes = [];
      let accepts = ['application/xml'];
      let returnType = 'Number.js';

      return this.apiClient.callApi(
        '/dragons/sum-age', 'GET',
        pathParams, queryParams, headerParams, formParams, postBody,
        authNames, contentTypes, accepts, returnType, callback
      );
    }
    /**
     * Callback function to receive the result of the personsPersonIdDelete operation.
     * @callback moduleapi/CRUDApi~personsPersonIdDeleteCallback
     * @param {String} error Error message, if any.
     * @param data This operation does not return a value.
     * @param {String} response The complete HTTP response.
     */

    /**
     * Delete person by ID
     * @param {Number} personId 
     * @param {module:api/CRUDApi~personsPersonIdDeleteCallback} callback The callback function, accepting three arguments: error, data, response
     */
    personsPersonIdDelete(personId, callback) {
      
      let postBody = null;
      // verify the required parameter 'personId' is set
      if (personId === undefined || personId === null) {
        throw new Error("Missing the required parameter 'personId' when calling personsPersonIdDelete");
      }

      let pathParams = {
        'personId': personId
      };
      let queryParams = {
        
      };
      let headerParams = {
        
      };
      let formParams = {
        
      };

      let authNames = [];
      let contentTypes = [];
      let accepts = [];
      let returnType = null;

      return this.apiClient.callApi(
        '/persons/{personId}', 'DELETE',
        pathParams, queryParams, headerParams, formParams, postBody,
        authNames, contentTypes, accepts, returnType, callback
      );
    }
    /**
     * Callback function to receive the result of the personsPersonIdGet operation.
     * @callback moduleapi/CRUDApi~personsPersonIdGetCallback
     * @param {String} error Error message, if any.
     * @param {module:model/Person{ data The data returned by the service call.
     * @param {String} response The complete HTTP response.
     */

    /**
     * Get person by ID
     * @param {Number} personId 
     * @param {module:api/CRUDApi~personsPersonIdGetCallback} callback The callback function, accepting three arguments: error, data, response
     * data is of type: {@link <&vendorExtensions.x-jsdoc-type>}
     */
    personsPersonIdGet(personId, callback) {
      
      let postBody = null;
      // verify the required parameter 'personId' is set
      if (personId === undefined || personId === null) {
        throw new Error("Missing the required parameter 'personId' when calling personsPersonIdGet");
      }

      let pathParams = {
        'personId': personId
      };
      let queryParams = {
        
      };
      let headerParams = {
        
      };
      let formParams = {
        
      };

      let authNames = [];
      let contentTypes = [];
      let accepts = ['application/xml'];
      let returnType = Person;

      return this.apiClient.callApi(
        '/persons/{personId}', 'GET',
        pathParams, queryParams, headerParams, formParams, postBody,
        authNames, contentTypes, accepts, returnType, callback
      );
    }
    /**
     * Callback function to receive the result of the personsPersonIdPut operation.
     * @callback moduleapi/CRUDApi~personsPersonIdPutCallback
     * @param {String} error Error message, if any.
     * @param data This operation does not return a value.
     * @param {String} response The complete HTTP response.
     */

    /**
     * Update person by ID
     * @param {Number} personId 
     * @param {module:model/Person} person 
     * @param {module:api/CRUDApi~personsPersonIdPutCallback} callback The callback function, accepting three arguments: error, data, response
     */
    personsPersonIdPut(personId, person, callback) {
      
      let postBody = null;
      // verify the required parameter 'personId' is set
      if (personId === undefined || personId === null) {
        throw new Error("Missing the required parameter 'personId' when calling personsPersonIdPut");
      }
      // verify the required parameter 'person' is set
      if (person === undefined || person === null) {
        throw new Error("Missing the required parameter 'person' when calling personsPersonIdPut");
      }

      let pathParams = {
        'personId': personId
      };
      let queryParams = {
        'person': person
      };
      let headerParams = {
        
      };
      let formParams = {
        
      };

      let authNames = [];
      let contentTypes = [];
      let accepts = ['application/xml'];
      let returnType = null;

      return this.apiClient.callApi(
        '/persons/{personId}', 'PUT',
        pathParams, queryParams, headerParams, formParams, postBody,
        authNames, contentTypes, accepts, returnType, callback
      );
    }

}