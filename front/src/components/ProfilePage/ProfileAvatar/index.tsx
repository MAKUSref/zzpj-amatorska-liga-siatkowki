import { Avatar, Chip, Typography } from '@mui/material';
import { useGetSelfInfoQuery } from '../../../features/api/apiSlice';
import { stringAvatar } from '../../../utils';
import PersonIcon from '@mui/icons-material/Person';
import EmailIcon from '@mui/icons-material/Email';
import LoginIcon from '@mui/icons-material/Login';
import './style.scss';

const ProfileAvatar = () => {
  const { data: selfInfo } = useGetSelfInfoQuery();

  return (
    <>
      <div className="profile-avatar-container d-flex align-items-center my-3 flex-column">
        <Avatar
          className="profile-avatar shadow-sm mb-3"
          {...stringAvatar(
            `${selfInfo?.payload?.name?.toUpperCase()} ${selfInfo?.payload?.lastname?.toUpperCase()}`
          )}>
          <PersonIcon className="profile-avatar-icon" />
        </Avatar>
        <Typography sx={{ fontSize: 14 }} color="text.secondary">
          <EmailIcon sx={{ fontSize: 16 }} />
          <span className="ms-2 me-4">{selfInfo?.payload?.email}</span>
          <LoginIcon sx={{ fontSize: 16 }} />
          <span className="ms-2">{selfInfo?.payload?.login}</span>
        </Typography>

        <h5 className="mt-2">
          {selfInfo?.payload.name} {selfInfo?.payload.lastname}
        </h5>

        <div className="mt-1">
          {selfInfo?.payload.roles?.map((role, i) => (
            <Chip className="fw-bold" key={i} label={role.role} />
          ))}
        </div>
      </div>
    </>
  );
};

export default ProfileAvatar;
