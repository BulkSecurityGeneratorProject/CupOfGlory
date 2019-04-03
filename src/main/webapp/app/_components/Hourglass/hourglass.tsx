import React from 'react';
import { RouteComponentProps } from 'react-router-dom';
import { connect } from 'react-redux';

import { getEntity } from 'app/entities/house/house.reducer';
import { IRootState } from 'app/shared/reducers';

import 'app/_components/Hourglass/hourglass.scss';

// export interface IHourglassProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class Hourglass extends React.Component {
    render() {
        return (
            <div className='box'>
                {/* House div, put color */}
                <div>
                    <div className="top-glass"></div>
                    <div className="top-glassp"></div>
                    <div className="glassp"></div>
                    <div className="middle"></div>
                    <div className="glass"></div>
                    <div className="band"></div>
                    <div className="band5"></div>
                    <div className="podium"></div>
                    <div className="points"></div>
                    <div className="points2"></div>
                    <div className="points3"></div>
                </div>
            </div>
        );
    }
}

/* const mapStateToProps = ({ house }: IRootState) => ({
    houseEntity: house.entity
});
const mapDispatchToProps = { getEntity };
type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;
*/
export default connect(
    // mapStateToProps,
    // mapDispatchToProps
)(Hourglass);
