import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IHouse, defaultValue } from 'app/shared/model/house.model';

export const ACTION_TYPES = {
  FETCH_HOUSE_LIST: 'house/FETCH_HOUSE_LIST',
  FETCH_HOUSE: 'house/FETCH_HOUSE',
  CREATE_HOUSE: 'house/CREATE_HOUSE',
  UPDATE_HOUSE: 'house/UPDATE_HOUSE',
  DELETE_HOUSE: 'house/DELETE_HOUSE',
  RESET: 'house/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IHouse>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type HouseState = Readonly<typeof initialState>;

// Reducer

export default (state: HouseState = initialState, action): HouseState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_HOUSE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_HOUSE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_HOUSE):
    case REQUEST(ACTION_TYPES.UPDATE_HOUSE):
    case REQUEST(ACTION_TYPES.DELETE_HOUSE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_HOUSE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_HOUSE):
    case FAILURE(ACTION_TYPES.CREATE_HOUSE):
    case FAILURE(ACTION_TYPES.UPDATE_HOUSE):
    case FAILURE(ACTION_TYPES.DELETE_HOUSE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_HOUSE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_HOUSE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_HOUSE):
    case SUCCESS(ACTION_TYPES.UPDATE_HOUSE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_HOUSE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/houses';

// Actions

export const getEntities: ICrudGetAllAction<IHouse> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_HOUSE_LIST,
  payload: axios.get<IHouse>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IHouse> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_HOUSE,
    payload: axios.get<IHouse>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IHouse> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_HOUSE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IHouse> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_HOUSE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IHouse> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_HOUSE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
