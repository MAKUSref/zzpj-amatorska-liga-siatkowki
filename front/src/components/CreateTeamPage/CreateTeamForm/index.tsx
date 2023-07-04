import * as Yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import { Alert, Button, Card, CardContent, TextField } from '@mui/material';
import { useForm } from 'react-hook-form';
import { toast } from 'react-toastify';
import { useTranslation } from 'react-i18next';
import { useCreateTeamMutation, useDoesHaveATeamQuery } from '../../../features/api/apiSlice';
import { Row } from 'react-bootstrap';

interface CreateTeamSchema {
  teamName: string;
  city: string;
}

const CreateTeamForm = () => {
  const [createTeam] = useCreateTeamMutation();
  const { t } = useTranslation();

  const { data: doesHaveATeam } = useDoesHaveATeamQuery();

  const createTeamSchema = Yup.object().shape({
    teamName: Yup.string().required(t('create_team.validation.team_name.required') as string),
    city: Yup.string().required(t('create_team.validation.city.required') as string)
  });

  const {
    register,
    handleSubmit,
    formState: { errors }
  } = useForm<CreateTeamSchema>({
    resolver: yupResolver(createTeamSchema)
  });

  const handleCreateTeam = ({ teamName, city }: CreateTeamSchema) => {
    createTeam({
      teamName,
      city
    })
      .unwrap()
      .then(() => {
        toast.success(t('create_team.succeed'));
      })
      .catch((err) => {
        toast.error(t(err.data));
      });
  };

  return (
    <Row className="mt-5">
      <Card>
        <CardContent>
          {!doesHaveATeam ? (
            <form onSubmit={handleSubmit(handleCreateTeam)}>
              <div className="mt-3">
                <TextField
                  {...register('teamName')}
                  label={t('create_team.team_name_input')}
                  size="small"
                  fullWidth
                  error={!!errors.teamName}
                  helperText={errors.teamName?.message}
                />
              </div>
              <div className="mt-3">
                <TextField
                  {...register('city')}
                  label={t('create_team.team_city_input')}
                  size="small"
                  fullWidth
                  error={!!errors.city}
                  helperText={errors.city?.message}
                />
              </div>
              <div className="mt-3 text-center">
                <Button variant="contained" type="submit">
                  {t('create_team.submit_button_label')}
                </Button>
              </div>
            </form>
          ) : (
            <Alert severity="error">{t('create_team.already_created')}</Alert>
          )}
        </CardContent>
      </Card>
    </Row>
  );
};

export default CreateTeamForm;
