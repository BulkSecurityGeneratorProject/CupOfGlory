import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './house.reducer';
import { IHouse } from 'app/shared/model/house.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IHouseDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class HouseDetail extends React.Component<IHouseDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { houseEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="cupOfGloryApp.house.detail.title">House</Translate> [<b>{houseEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="cupOfGloryApp.house.name">Name</Translate>
              </span>
            </dt>
            <dd>{houseEntity.name}</dd>
            <dt>
              <span id="color">
                <Translate contentKey="cupOfGloryApp.house.color">Color</Translate>
              </span>
            </dt>
            <dd>{houseEntity.color}</dd>
            <dt>
              <span id="score">
                <Translate contentKey="cupOfGloryApp.house.score">Score</Translate>
              </span>
            </dt>
            <dd>{houseEntity.score}</dd>
            <dt>
              <Translate contentKey="cupOfGloryApp.house.school">School</Translate>
            </dt>
            <dd>{houseEntity.schoolName ? houseEntity.schoolName : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/house" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/house/${houseEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ house }: IRootState) => ({
  houseEntity: house.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(HouseDetail);
