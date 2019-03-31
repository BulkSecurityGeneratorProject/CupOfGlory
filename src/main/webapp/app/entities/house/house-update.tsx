import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ISchool } from 'app/shared/model/school.model';
import { getEntities as getSchools } from 'app/entities/school/school.reducer';
import { getEntity, updateEntity, createEntity, reset } from './house.reducer';
import { IHouse } from 'app/shared/model/house.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IHouseUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IHouseUpdateState {
  isNew: boolean;
  schoolId: string;
}

export class HouseUpdate extends React.Component<IHouseUpdateProps, IHouseUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      schoolId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getSchools();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { houseEntity } = this.props;
      const entity = {
        ...houseEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/house');
  };

  render() {
    const { houseEntity, schools, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="cupOfGloryApp.house.home.createOrEditLabel">
              <Translate contentKey="cupOfGloryApp.house.home.createOrEditLabel">Create or edit a House</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : houseEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="house-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="cupOfGloryApp.house.name">Name</Translate>
                  </Label>
                  <AvField
                    id="house-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      minLength: { value: 1, errorMessage: translate('entity.validation.minlength', { min: 1 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="colorLabel" for="color">
                    <Translate contentKey="cupOfGloryApp.house.color">Color</Translate>
                  </Label>
                  <AvField
                    id="house-color"
                    type="text"
                    name="color"
                    validate={{
                      minLength: { value: 7, errorMessage: translate('entity.validation.minlength', { min: 7 }) },
                      maxLength: { value: 7, errorMessage: translate('entity.validation.maxlength', { max: 7 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="scoreLabel" for="score">
                    <Translate contentKey="cupOfGloryApp.house.score">Score</Translate>
                  </Label>
                  <AvField id="house-score" type="string" className="form-control" name="score" />
                </AvGroup>
                <AvGroup>
                  <Label for="school.name">
                    <Translate contentKey="cupOfGloryApp.house.school">School</Translate>
                  </Label>
                  <AvInput id="house-school" type="select" className="form-control" name="schoolId">
                    {schools
                      ? schools.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/house" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  schools: storeState.school.entities,
  houseEntity: storeState.house.entity,
  loading: storeState.house.loading,
  updating: storeState.house.updating,
  updateSuccess: storeState.house.updateSuccess
});

const mapDispatchToProps = {
  getSchools,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(HouseUpdate);
