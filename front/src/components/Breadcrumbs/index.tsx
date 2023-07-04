import MuiBreadcrumbs from '@mui/material/Breadcrumbs';
import { NavLink } from 'react-router-dom';

interface BreadcrumbsProps {
  paths: {
    label: string;
    to: string;
  }[];
}

const Breadcrumbs = ({ paths }: BreadcrumbsProps) => {
  return (
    <div className="mb-3 mt-2">
      <MuiBreadcrumbs aria-label="breadcrumb">
        {paths.map((path, i) => (
          <NavLink className="text-muted text-decoration-none" to={path.to} key={i}>
            {path.label}
          </NavLink>
        ))}
      </MuiBreadcrumbs>
    </div>
  );
};

export default Breadcrumbs;
