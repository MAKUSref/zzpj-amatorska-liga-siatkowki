import {
  Box,
  CardContent,
  Typography,
  List,
  ListItem,
  Checkbox,
  ListItemText,
  ListItemButton
} from '@mui/material';
import { useState } from 'react';

export interface ElementState {
  id: string;
  title: string;
  isChecked: boolean;
}

interface ListCheckedViewProps {
  title: string;
  elements: ElementState[];
}

const ListCheckedView = ({ title, elements }: ListCheckedViewProps) => {
  const [checkedState, setCheckedState] = useState<ElementState[]>(elements);

  const handleToggle = (element: ElementState) => () => {
    element.isChecked = !element.isChecked;
    setCheckedState([...checkedState.filter((st) => st.id !== element.id), element]);
  };

  return (
    <>
      <Box>
        <CardContent>
          <Typography gutterBottom variant="h5" component="div">
            {title}
          </Typography>
          <List>
            {elements.map((element, i) => (
              <ListItem key={i} disablePadding>
                <ListItemButton onClick={handleToggle(element)} dense>
                  <Checkbox edge="start" checked={element.isChecked} tabIndex={-1} disableRipple />
                  <ListItemText primary={element.title} />
                </ListItemButton>
              </ListItem>
            ))}
          </List>
        </CardContent>
      </Box>
    </>
  );
};

export default ListCheckedView;
